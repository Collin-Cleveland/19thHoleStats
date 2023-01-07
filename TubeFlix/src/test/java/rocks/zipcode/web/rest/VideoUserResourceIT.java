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
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.repository.VideoUserRepository;
import rocks.zipcode.service.dto.VideoUserDTO;
import rocks.zipcode.service.mapper.VideoUserMapper;

/**
 * Integration tests for the {@link VideoUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideoUserResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/video-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VideoUserRepository videoUserRepository;

    @Autowired
    private VideoUserMapper videoUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoUserMockMvc;

    private VideoUser videoUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoUser createEntity(EntityManager em) {
        VideoUser videoUser = new VideoUser().userName(DEFAULT_USER_NAME);
        return videoUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoUser createUpdatedEntity(EntityManager em) {
        VideoUser videoUser = new VideoUser().userName(UPDATED_USER_NAME);
        return videoUser;
    }

    @BeforeEach
    public void initTest() {
        videoUser = createEntity(em);
    }

    @Test
    @Transactional
    void createVideoUser() throws Exception {
        int databaseSizeBeforeCreate = videoUserRepository.findAll().size();
        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);
        restVideoUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoUserDTO)))
            .andExpect(status().isCreated());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeCreate + 1);
        VideoUser testVideoUser = videoUserList.get(videoUserList.size() - 1);
        assertThat(testVideoUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    void createVideoUserWithExistingId() throws Exception {
        // Create the VideoUser with an existing ID
        videoUser.setId(1L);
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        int databaseSizeBeforeCreate = videoUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoUserRepository.findAll().size();
        // set the field null
        videoUser.setUserName(null);

        // Create the VideoUser, which fails.
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        restVideoUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoUserDTO)))
            .andExpect(status().isBadRequest());

        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVideoUsers() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        // Get all the videoUserList
        restVideoUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)));
    }

    @Test
    @Transactional
    void getVideoUser() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        // Get the videoUser
        restVideoUserMockMvc
            .perform(get(ENTITY_API_URL_ID, videoUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME));
    }

    @Test
    @Transactional
    void getNonExistingVideoUser() throws Exception {
        // Get the videoUser
        restVideoUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVideoUser() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();

        // Update the videoUser
        VideoUser updatedVideoUser = videoUserRepository.findById(videoUser.getId()).get();
        // Disconnect from session so that the updates on updatedVideoUser are not directly saved in db
        em.detach(updatedVideoUser);
        updatedVideoUser.userName(UPDATED_USER_NAME);
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(updatedVideoUser);

        restVideoUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
        VideoUser testVideoUser = videoUserList.get(videoUserList.size() - 1);
        assertThat(testVideoUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void putNonExistingVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(videoUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideoUserWithPatch() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();

        // Update the videoUser using partial update
        VideoUser partialUpdatedVideoUser = new VideoUser();
        partialUpdatedVideoUser.setId(videoUser.getId());

        partialUpdatedVideoUser.userName(UPDATED_USER_NAME);

        restVideoUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideoUser))
            )
            .andExpect(status().isOk());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
        VideoUser testVideoUser = videoUserList.get(videoUserList.size() - 1);
        assertThat(testVideoUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void fullUpdateVideoUserWithPatch() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();

        // Update the videoUser using partial update
        VideoUser partialUpdatedVideoUser = new VideoUser();
        partialUpdatedVideoUser.setId(videoUser.getId());

        partialUpdatedVideoUser.userName(UPDATED_USER_NAME);

        restVideoUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideoUser))
            )
            .andExpect(status().isOk());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
        VideoUser testVideoUser = videoUserList.get(videoUserList.size() - 1);
        assertThat(testVideoUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videoUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideoUser() throws Exception {
        int databaseSizeBeforeUpdate = videoUserRepository.findAll().size();
        videoUser.setId(count.incrementAndGet());

        // Create the VideoUser
        VideoUserDTO videoUserDTO = videoUserMapper.toDto(videoUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(videoUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoUser in the database
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideoUser() throws Exception {
        // Initialize the database
        videoUserRepository.saveAndFlush(videoUser);

        int databaseSizeBeforeDelete = videoUserRepository.findAll().size();

        // Delete the videoUser
        restVideoUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, videoUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VideoUser> videoUserList = videoUserRepository.findAll();
        assertThat(videoUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
