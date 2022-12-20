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
import rocks.zipcode.domain.Hole;
import rocks.zipcode.repository.HoleRepository;

/**
 * Integration tests for the {@link HoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoleResourceIT {

    private static final Integer DEFAULT_HOLE_NUMBER = 18;
    private static final Integer UPDATED_HOLE_NUMBER = 17;

    private static final Integer DEFAULT_PAR = 1;
    private static final Integer UPDATED_PAR = 2;

    private static final String ENTITY_API_URL = "/api/holes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoleRepository holeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoleMockMvc;

    private Hole hole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hole createEntity(EntityManager em) {
        Hole hole = new Hole().holeNumber(DEFAULT_HOLE_NUMBER).par(DEFAULT_PAR);
        return hole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hole createUpdatedEntity(EntityManager em) {
        Hole hole = new Hole().holeNumber(UPDATED_HOLE_NUMBER).par(UPDATED_PAR);
        return hole;
    }

    @BeforeEach
    public void initTest() {
        hole = createEntity(em);
    }

    @Test
    @Transactional
    void createHole() throws Exception {
        int databaseSizeBeforeCreate = holeRepository.findAll().size();
        // Create the Hole
        restHoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hole)))
            .andExpect(status().isCreated());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeCreate + 1);
        Hole testHole = holeList.get(holeList.size() - 1);
        assertThat(testHole.getHoleNumber()).isEqualTo(DEFAULT_HOLE_NUMBER);
        assertThat(testHole.getPar()).isEqualTo(DEFAULT_PAR);
    }

    @Test
    @Transactional
    void createHoleWithExistingId() throws Exception {
        // Create the Hole with an existing ID
        hole.setId(1L);

        int databaseSizeBeforeCreate = holeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hole)))
            .andExpect(status().isBadRequest());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoles() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        // Get all the holeList
        restHoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hole.getId().intValue())))
            .andExpect(jsonPath("$.[*].holeNumber").value(hasItem(DEFAULT_HOLE_NUMBER)))
            .andExpect(jsonPath("$.[*].par").value(hasItem(DEFAULT_PAR)));
    }

    @Test
    @Transactional
    void getHole() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        // Get the hole
        restHoleMockMvc
            .perform(get(ENTITY_API_URL_ID, hole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hole.getId().intValue()))
            .andExpect(jsonPath("$.holeNumber").value(DEFAULT_HOLE_NUMBER))
            .andExpect(jsonPath("$.par").value(DEFAULT_PAR));
    }

    @Test
    @Transactional
    void getNonExistingHole() throws Exception {
        // Get the hole
        restHoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHole() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        int databaseSizeBeforeUpdate = holeRepository.findAll().size();

        // Update the hole
        Hole updatedHole = holeRepository.findById(hole.getId()).get();
        // Disconnect from session so that the updates on updatedHole are not directly saved in db
        em.detach(updatedHole);
        updatedHole.holeNumber(UPDATED_HOLE_NUMBER).par(UPDATED_PAR);

        restHoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHole))
            )
            .andExpect(status().isOk());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
        Hole testHole = holeList.get(holeList.size() - 1);
        assertThat(testHole.getHoleNumber()).isEqualTo(UPDATED_HOLE_NUMBER);
        assertThat(testHole.getPar()).isEqualTo(UPDATED_PAR);
    }

    @Test
    @Transactional
    void putNonExistingHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoleWithPatch() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        int databaseSizeBeforeUpdate = holeRepository.findAll().size();

        // Update the hole using partial update
        Hole partialUpdatedHole = new Hole();
        partialUpdatedHole.setId(hole.getId());

        partialUpdatedHole.par(UPDATED_PAR);

        restHoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHole))
            )
            .andExpect(status().isOk());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
        Hole testHole = holeList.get(holeList.size() - 1);
        assertThat(testHole.getHoleNumber()).isEqualTo(DEFAULT_HOLE_NUMBER);
        assertThat(testHole.getPar()).isEqualTo(UPDATED_PAR);
    }

    @Test
    @Transactional
    void fullUpdateHoleWithPatch() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        int databaseSizeBeforeUpdate = holeRepository.findAll().size();

        // Update the hole using partial update
        Hole partialUpdatedHole = new Hole();
        partialUpdatedHole.setId(hole.getId());

        partialUpdatedHole.holeNumber(UPDATED_HOLE_NUMBER).par(UPDATED_PAR);

        restHoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHole))
            )
            .andExpect(status().isOk());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
        Hole testHole = holeList.get(holeList.size() - 1);
        assertThat(testHole.getHoleNumber()).isEqualTo(UPDATED_HOLE_NUMBER);
        assertThat(testHole.getPar()).isEqualTo(UPDATED_PAR);
    }

    @Test
    @Transactional
    void patchNonExistingHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hole))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHole() throws Exception {
        int databaseSizeBeforeUpdate = holeRepository.findAll().size();
        hole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hole)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hole in the database
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHole() throws Exception {
        // Initialize the database
        holeRepository.saveAndFlush(hole);

        int databaseSizeBeforeDelete = holeRepository.findAll().size();

        // Delete the hole
        restHoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, hole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hole> holeList = holeRepository.findAll();
        assertThat(holeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
