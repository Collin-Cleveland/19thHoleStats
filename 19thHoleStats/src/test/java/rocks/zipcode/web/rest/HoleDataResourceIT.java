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
import rocks.zipcode.domain.HoleData;
import rocks.zipcode.repository.HoleDataRepository;

/**
 * Integration tests for the {@link HoleDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoleDataResourceIT {

    private static final Integer DEFAULT_HOLE_SCORE = 1;
    private static final Integer UPDATED_HOLE_SCORE = 2;

    private static final Integer DEFAULT_PUTTS = 1;
    private static final Integer UPDATED_PUTTS = 2;

    private static final Boolean DEFAULT_FAIRWAY_HIT = false;
    private static final Boolean UPDATED_FAIRWAY_HIT = true;

    private static final String ENTITY_API_URL = "/api/hole-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoleDataRepository holeDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoleDataMockMvc;

    private HoleData holeData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoleData createEntity(EntityManager em) {
        HoleData holeData = new HoleData().holeScore(DEFAULT_HOLE_SCORE).putts(DEFAULT_PUTTS).fairwayHit(DEFAULT_FAIRWAY_HIT);
        return holeData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoleData createUpdatedEntity(EntityManager em) {
        HoleData holeData = new HoleData().holeScore(UPDATED_HOLE_SCORE).putts(UPDATED_PUTTS).fairwayHit(UPDATED_FAIRWAY_HIT);
        return holeData;
    }

    @BeforeEach
    public void initTest() {
        holeData = createEntity(em);
    }

    @Test
    @Transactional
    void createHoleData() throws Exception {
        int databaseSizeBeforeCreate = holeDataRepository.findAll().size();
        // Create the HoleData
        restHoleDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holeData)))
            .andExpect(status().isCreated());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeCreate + 1);
        HoleData testHoleData = holeDataList.get(holeDataList.size() - 1);
        assertThat(testHoleData.getHoleScore()).isEqualTo(DEFAULT_HOLE_SCORE);
        assertThat(testHoleData.getPutts()).isEqualTo(DEFAULT_PUTTS);
        assertThat(testHoleData.getFairwayHit()).isEqualTo(DEFAULT_FAIRWAY_HIT);
    }

    @Test
    @Transactional
    void createHoleDataWithExistingId() throws Exception {
        // Create the HoleData with an existing ID
        holeData.setId(1L);

        int databaseSizeBeforeCreate = holeDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoleDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holeData)))
            .andExpect(status().isBadRequest());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoleData() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        // Get all the holeDataList
        restHoleDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holeData.getId().intValue())))
            .andExpect(jsonPath("$.[*].holeScore").value(hasItem(DEFAULT_HOLE_SCORE)))
            .andExpect(jsonPath("$.[*].putts").value(hasItem(DEFAULT_PUTTS)))
            .andExpect(jsonPath("$.[*].fairwayHit").value(hasItem(DEFAULT_FAIRWAY_HIT.booleanValue())));
    }

    @Test
    @Transactional
    void getHoleData() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        // Get the holeData
        restHoleDataMockMvc
            .perform(get(ENTITY_API_URL_ID, holeData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holeData.getId().intValue()))
            .andExpect(jsonPath("$.holeScore").value(DEFAULT_HOLE_SCORE))
            .andExpect(jsonPath("$.putts").value(DEFAULT_PUTTS))
            .andExpect(jsonPath("$.fairwayHit").value(DEFAULT_FAIRWAY_HIT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingHoleData() throws Exception {
        // Get the holeData
        restHoleDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHoleData() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();

        // Update the holeData
        HoleData updatedHoleData = holeDataRepository.findById(holeData.getId()).get();
        // Disconnect from session so that the updates on updatedHoleData are not directly saved in db
        em.detach(updatedHoleData);
        updatedHoleData.holeScore(UPDATED_HOLE_SCORE).putts(UPDATED_PUTTS).fairwayHit(UPDATED_FAIRWAY_HIT);

        restHoleDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoleData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHoleData))
            )
            .andExpect(status().isOk());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
        HoleData testHoleData = holeDataList.get(holeDataList.size() - 1);
        assertThat(testHoleData.getHoleScore()).isEqualTo(UPDATED_HOLE_SCORE);
        assertThat(testHoleData.getPutts()).isEqualTo(UPDATED_PUTTS);
        assertThat(testHoleData.getFairwayHit()).isEqualTo(UPDATED_FAIRWAY_HIT);
    }

    @Test
    @Transactional
    void putNonExistingHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holeData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holeData))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holeData))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holeData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoleDataWithPatch() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();

        // Update the holeData using partial update
        HoleData partialUpdatedHoleData = new HoleData();
        partialUpdatedHoleData.setId(holeData.getId());

        partialUpdatedHoleData.putts(UPDATED_PUTTS).fairwayHit(UPDATED_FAIRWAY_HIT);

        restHoleDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoleData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoleData))
            )
            .andExpect(status().isOk());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
        HoleData testHoleData = holeDataList.get(holeDataList.size() - 1);
        assertThat(testHoleData.getHoleScore()).isEqualTo(DEFAULT_HOLE_SCORE);
        assertThat(testHoleData.getPutts()).isEqualTo(UPDATED_PUTTS);
        assertThat(testHoleData.getFairwayHit()).isEqualTo(UPDATED_FAIRWAY_HIT);
    }

    @Test
    @Transactional
    void fullUpdateHoleDataWithPatch() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();

        // Update the holeData using partial update
        HoleData partialUpdatedHoleData = new HoleData();
        partialUpdatedHoleData.setId(holeData.getId());

        partialUpdatedHoleData.holeScore(UPDATED_HOLE_SCORE).putts(UPDATED_PUTTS).fairwayHit(UPDATED_FAIRWAY_HIT);

        restHoleDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoleData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoleData))
            )
            .andExpect(status().isOk());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
        HoleData testHoleData = holeDataList.get(holeDataList.size() - 1);
        assertThat(testHoleData.getHoleScore()).isEqualTo(UPDATED_HOLE_SCORE);
        assertThat(testHoleData.getPutts()).isEqualTo(UPDATED_PUTTS);
        assertThat(testHoleData.getFairwayHit()).isEqualTo(UPDATED_FAIRWAY_HIT);
    }

    @Test
    @Transactional
    void patchNonExistingHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, holeData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holeData))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holeData))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoleData() throws Exception {
        int databaseSizeBeforeUpdate = holeDataRepository.findAll().size();
        holeData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(holeData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoleData in the database
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoleData() throws Exception {
        // Initialize the database
        holeDataRepository.saveAndFlush(holeData);

        int databaseSizeBeforeDelete = holeDataRepository.findAll().size();

        // Delete the holeData
        restHoleDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, holeData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoleData> holeDataList = holeDataRepository.findAll();
        assertThat(holeDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
