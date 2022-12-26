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
import rocks.zipcode.domain.Golfer;
import rocks.zipcode.repository.GolferRepository;
import rocks.zipcode.service.dto.GolferDTO;
import rocks.zipcode.service.mapper.GolferMapper;

/**
 * Integration tests for the {@link GolferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GolferResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AVG_SCORE = 1D;
    private static final Double UPDATED_AVG_SCORE = 2D;

    private static final Double DEFAULT_ROUNDS_PLAYED = 1D;
    private static final Double UPDATED_ROUNDS_PLAYED = 2D;

    private static final Double DEFAULT_HANDICAP = 1D;
    private static final Double UPDATED_HANDICAP = 2D;

    private static final String ENTITY_API_URL = "/api/golfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GolferRepository golferRepository;

    @Autowired
    private GolferMapper golferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGolferMockMvc;

    private Golfer golfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Golfer createEntity(EntityManager em) {
        Golfer golfer = new Golfer()
            .name(DEFAULT_NAME)
            .avgScore(DEFAULT_AVG_SCORE)
            .roundsPlayed(DEFAULT_ROUNDS_PLAYED)
            .handicap(DEFAULT_HANDICAP);
        return golfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Golfer createUpdatedEntity(EntityManager em) {
        Golfer golfer = new Golfer()
            .name(UPDATED_NAME)
            .avgScore(UPDATED_AVG_SCORE)
            .roundsPlayed(UPDATED_ROUNDS_PLAYED)
            .handicap(UPDATED_HANDICAP);
        return golfer;
    }

    @BeforeEach
    public void initTest() {
        golfer = createEntity(em);
    }

    @Test
    @Transactional
    void createGolfer() throws Exception {
        int databaseSizeBeforeCreate = golferRepository.findAll().size();
        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);
        restGolferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golferDTO)))
            .andExpect(status().isCreated());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeCreate + 1);
        Golfer testGolfer = golferList.get(golferList.size() - 1);
        assertThat(testGolfer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGolfer.getAvgScore()).isEqualTo(DEFAULT_AVG_SCORE);
        assertThat(testGolfer.getRoundsPlayed()).isEqualTo(DEFAULT_ROUNDS_PLAYED);
        assertThat(testGolfer.getHandicap()).isEqualTo(DEFAULT_HANDICAP);
    }

    @Test
    @Transactional
    void createGolferWithExistingId() throws Exception {
        // Create the Golfer with an existing ID
        golfer.setId(1L);
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        int databaseSizeBeforeCreate = golferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGolferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = golferRepository.findAll().size();
        // set the field null
        golfer.setName(null);

        // Create the Golfer, which fails.
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        restGolferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golferDTO)))
            .andExpect(status().isBadRequest());

        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGolfers() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        // Get all the golferList
        restGolferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(golfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].avgScore").value(hasItem(DEFAULT_AVG_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].roundsPlayed").value(hasItem(DEFAULT_ROUNDS_PLAYED.doubleValue())))
            .andExpect(jsonPath("$.[*].handicap").value(hasItem(DEFAULT_HANDICAP.doubleValue())));
    }

    @Test
    @Transactional
    void getGolfer() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        // Get the golfer
        restGolferMockMvc
            .perform(get(ENTITY_API_URL_ID, golfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(golfer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.avgScore").value(DEFAULT_AVG_SCORE.doubleValue()))
            .andExpect(jsonPath("$.roundsPlayed").value(DEFAULT_ROUNDS_PLAYED.doubleValue()))
            .andExpect(jsonPath("$.handicap").value(DEFAULT_HANDICAP.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingGolfer() throws Exception {
        // Get the golfer
        restGolferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGolfer() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        int databaseSizeBeforeUpdate = golferRepository.findAll().size();

        // Update the golfer
        Golfer updatedGolfer = golferRepository.findById(golfer.getId()).get();
        // Disconnect from session so that the updates on updatedGolfer are not directly saved in db
        em.detach(updatedGolfer);
        updatedGolfer.name(UPDATED_NAME).avgScore(UPDATED_AVG_SCORE).roundsPlayed(UPDATED_ROUNDS_PLAYED).handicap(UPDATED_HANDICAP);
        GolferDTO golferDTO = golferMapper.toDto(updatedGolfer);

        restGolferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, golferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isOk());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
        Golfer testGolfer = golferList.get(golferList.size() - 1);
        assertThat(testGolfer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfer.getAvgScore()).isEqualTo(UPDATED_AVG_SCORE);
        assertThat(testGolfer.getRoundsPlayed()).isEqualTo(UPDATED_ROUNDS_PLAYED);
        assertThat(testGolfer.getHandicap()).isEqualTo(UPDATED_HANDICAP);
    }

    @Test
    @Transactional
    void putNonExistingGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, golferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(golferDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGolferWithPatch() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        int databaseSizeBeforeUpdate = golferRepository.findAll().size();

        // Update the golfer using partial update
        Golfer partialUpdatedGolfer = new Golfer();
        partialUpdatedGolfer.setId(golfer.getId());

        partialUpdatedGolfer.name(UPDATED_NAME).roundsPlayed(UPDATED_ROUNDS_PLAYED);

        restGolferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGolfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGolfer))
            )
            .andExpect(status().isOk());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
        Golfer testGolfer = golferList.get(golferList.size() - 1);
        assertThat(testGolfer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfer.getAvgScore()).isEqualTo(DEFAULT_AVG_SCORE);
        assertThat(testGolfer.getRoundsPlayed()).isEqualTo(UPDATED_ROUNDS_PLAYED);
        assertThat(testGolfer.getHandicap()).isEqualTo(DEFAULT_HANDICAP);
    }

    @Test
    @Transactional
    void fullUpdateGolferWithPatch() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        int databaseSizeBeforeUpdate = golferRepository.findAll().size();

        // Update the golfer using partial update
        Golfer partialUpdatedGolfer = new Golfer();
        partialUpdatedGolfer.setId(golfer.getId());

        partialUpdatedGolfer.name(UPDATED_NAME).avgScore(UPDATED_AVG_SCORE).roundsPlayed(UPDATED_ROUNDS_PLAYED).handicap(UPDATED_HANDICAP);

        restGolferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGolfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGolfer))
            )
            .andExpect(status().isOk());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
        Golfer testGolfer = golferList.get(golferList.size() - 1);
        assertThat(testGolfer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGolfer.getAvgScore()).isEqualTo(UPDATED_AVG_SCORE);
        assertThat(testGolfer.getRoundsPlayed()).isEqualTo(UPDATED_ROUNDS_PLAYED);
        assertThat(testGolfer.getHandicap()).isEqualTo(UPDATED_HANDICAP);
    }

    @Test
    @Transactional
    void patchNonExistingGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, golferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGolfer() throws Exception {
        int databaseSizeBeforeUpdate = golferRepository.findAll().size();
        golfer.setId(count.incrementAndGet());

        // Create the Golfer
        GolferDTO golferDTO = golferMapper.toDto(golfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGolferMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(golferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Golfer in the database
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGolfer() throws Exception {
        // Initialize the database
        golferRepository.saveAndFlush(golfer);

        int databaseSizeBeforeDelete = golferRepository.findAll().size();

        // Delete the golfer
        restGolferMockMvc
            .perform(delete(ENTITY_API_URL_ID, golfer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Golfer> golferList = golferRepository.findAll();
        assertThat(golferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
