package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.domain.enumeration.TeeColor;
import rocks.zipcode.repository.ScorecardRepository;
import rocks.zipcode.service.dto.ScorecardDTO;
import rocks.zipcode.service.mapper.ScorecardMapper;

/**
 * Integration tests for the {@link ScorecardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScorecardResourceIT {

    private static final TeeColor DEFAULT_TEE_COLOR = TeeColor.BLUE;
    private static final TeeColor UPDATED_TEE_COLOR = TeeColor.WHITE;

    private static final Integer DEFAULT_TOTAL_SCORE = 1;
    private static final Integer UPDATED_TOTAL_SCORE = 2;

    private static final Integer DEFAULT_TOTAL_PUTTS = 1;
    private static final Integer UPDATED_TOTAL_PUTTS = 2;

    private static final Integer DEFAULT_FAIRWAYS_HIT = 1;
    private static final Integer UPDATED_FAIRWAYS_HIT = 2;

    private static final String ENTITY_API_URL = "/api/scorecards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScorecardRepository scorecardRepository;

    @Autowired
    private ScorecardMapper scorecardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScorecardMockMvc;

    private Scorecard scorecard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scorecard createEntity(EntityManager em) {
        Scorecard scorecard = new Scorecard()
            .teeColor(DEFAULT_TEE_COLOR)
            .totalScore(DEFAULT_TOTAL_SCORE)
            .totalPutts(DEFAULT_TOTAL_PUTTS)
            .fairwaysHit(DEFAULT_FAIRWAYS_HIT);
        return scorecard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scorecard createUpdatedEntity(EntityManager em) {
        Scorecard scorecard = new Scorecard()
            .teeColor(UPDATED_TEE_COLOR)
            .totalScore(UPDATED_TOTAL_SCORE)
            .totalPutts(UPDATED_TOTAL_PUTTS)
            .fairwaysHit(UPDATED_FAIRWAYS_HIT);
        return scorecard;
    }

    @BeforeEach
    public void initTest() {
        scorecard = createEntity(em);
    }

    @Test
    @Transactional
    void createScorecard() throws Exception {
        int databaseSizeBeforeCreate = scorecardRepository.findAll().size();
        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);
        restScorecardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scorecardDTO)))
            .andExpect(status().isCreated());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeCreate + 1);
        Scorecard testScorecard = scorecardList.get(scorecardList.size() - 1);
        assertThat(testScorecard.getTeeColor()).isEqualTo(DEFAULT_TEE_COLOR);
        assertThat(testScorecard.getTotalScore()).isEqualTo(DEFAULT_TOTAL_SCORE);
        assertThat(testScorecard.getTotalPutts()).isEqualTo(DEFAULT_TOTAL_PUTTS);
        assertThat(testScorecard.getFairwaysHit()).isEqualTo(DEFAULT_FAIRWAYS_HIT);
    }

    @Test
    @Transactional
    void createScorecardWithExistingId() throws Exception {
        // Create the Scorecard with an existing ID
        scorecard.setId(1L);
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        int databaseSizeBeforeCreate = scorecardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScorecardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scorecardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScorecards() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        // Get all the scorecardList
        restScorecardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scorecard.getId().intValue())))
            .andExpect(jsonPath("$.[*].teeColor").value(hasItem(DEFAULT_TEE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].totalScore").value(hasItem(DEFAULT_TOTAL_SCORE)))
            .andExpect(jsonPath("$.[*].totalPutts").value(hasItem(DEFAULT_TOTAL_PUTTS)))
            .andExpect(jsonPath("$.[*].fairwaysHit").value(hasItem(DEFAULT_FAIRWAYS_HIT)));
    }

    @Test
    @Transactional
    void getScorecard() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        // Get the scorecard
        restScorecardMockMvc
            .perform(get(ENTITY_API_URL_ID, scorecard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scorecard.getId().intValue()))
            .andExpect(jsonPath("$.teeColor").value(DEFAULT_TEE_COLOR.toString()))
            .andExpect(jsonPath("$.totalScore").value(DEFAULT_TOTAL_SCORE))
            .andExpect(jsonPath("$.totalPutts").value(DEFAULT_TOTAL_PUTTS))
            .andExpect(jsonPath("$.fairwaysHit").value(DEFAULT_FAIRWAYS_HIT));
    }

    @Test
    @Transactional
    void getNonExistingScorecard() throws Exception {
        // Get the scorecard
        restScorecardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScorecard() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();

        // Update the scorecard
        Scorecard updatedScorecard = scorecardRepository.findById(scorecard.getId()).get();
        // Disconnect from session so that the updates on updatedScorecard are not directly saved in db
        em.detach(updatedScorecard);
        updatedScorecard
            .teeColor(UPDATED_TEE_COLOR)
            .totalScore(UPDATED_TOTAL_SCORE)
            .totalPutts(UPDATED_TOTAL_PUTTS)
            .fairwaysHit(UPDATED_FAIRWAYS_HIT);
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(updatedScorecard);

        restScorecardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scorecardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
        Scorecard testScorecard = scorecardList.get(scorecardList.size() - 1);
        assertThat(testScorecard.getTeeColor()).isEqualTo(UPDATED_TEE_COLOR);
        assertThat(testScorecard.getTotalScore()).isEqualTo(UPDATED_TOTAL_SCORE);
        assertThat(testScorecard.getTotalPutts()).isEqualTo(UPDATED_TOTAL_PUTTS);
        assertThat(testScorecard.getFairwaysHit()).isEqualTo(UPDATED_FAIRWAYS_HIT);
    }

    @Test
    @Transactional
    void putNonExistingScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scorecardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scorecardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScorecardWithPatch() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();

        // Update the scorecard using partial update
        Scorecard partialUpdatedScorecard = new Scorecard();
        partialUpdatedScorecard.setId(scorecard.getId());

        partialUpdatedScorecard.totalPutts(UPDATED_TOTAL_PUTTS);

        restScorecardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScorecard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScorecard))
            )
            .andExpect(status().isOk());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
        Scorecard testScorecard = scorecardList.get(scorecardList.size() - 1);
        assertThat(testScorecard.getTeeColor()).isEqualTo(DEFAULT_TEE_COLOR);
        assertThat(testScorecard.getTotalScore()).isEqualTo(DEFAULT_TOTAL_SCORE);
        assertThat(testScorecard.getTotalPutts()).isEqualTo(UPDATED_TOTAL_PUTTS);
        assertThat(testScorecard.getFairwaysHit()).isEqualTo(DEFAULT_FAIRWAYS_HIT);
    }

    @Test
    @Transactional
    void fullUpdateScorecardWithPatch() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();

        // Update the scorecard using partial update
        Scorecard partialUpdatedScorecard = new Scorecard();
        partialUpdatedScorecard.setId(scorecard.getId());

        partialUpdatedScorecard
            .teeColor(UPDATED_TEE_COLOR)
            .totalScore(UPDATED_TOTAL_SCORE)
            .totalPutts(UPDATED_TOTAL_PUTTS)
            .fairwaysHit(UPDATED_FAIRWAYS_HIT);

        restScorecardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScorecard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScorecard))
            )
            .andExpect(status().isOk());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
        Scorecard testScorecard = scorecardList.get(scorecardList.size() - 1);
        assertThat(testScorecard.getTeeColor()).isEqualTo(UPDATED_TEE_COLOR);
        assertThat(testScorecard.getTotalScore()).isEqualTo(UPDATED_TOTAL_SCORE);
        assertThat(testScorecard.getTotalPutts()).isEqualTo(UPDATED_TOTAL_PUTTS);
        assertThat(testScorecard.getFairwaysHit()).isEqualTo(UPDATED_FAIRWAYS_HIT);
    }

    @Test
    @Transactional
    void patchNonExistingScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scorecardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScorecard() throws Exception {
        int databaseSizeBeforeUpdate = scorecardRepository.findAll().size();
        scorecard.setId(count.incrementAndGet());

        // Create the Scorecard
        ScorecardDTO scorecardDTO = scorecardMapper.toDto(scorecard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScorecardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scorecardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scorecard in the database
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScorecard() throws Exception {
        // Initialize the database
        scorecardRepository.saveAndFlush(scorecard);

        int databaseSizeBeforeDelete = scorecardRepository.findAll().size();

        // Delete the scorecard
        restScorecardMockMvc
            .perform(delete(ENTITY_API_URL_ID, scorecard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scorecard> scorecardList = scorecardRepository.findAll();
        assertThat(scorecardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
