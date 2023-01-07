package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.VideoUserRepository;
import rocks.zipcode.service.VideoUserService;
import rocks.zipcode.service.dto.VideoUserDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.VideoUser}.
 */
@RestController
@RequestMapping("/api")
public class VideoUserResource {

    private final Logger log = LoggerFactory.getLogger(VideoUserResource.class);

    private static final String ENTITY_NAME = "videoUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoUserService videoUserService;

    private final VideoUserRepository videoUserRepository;

    public VideoUserResource(VideoUserService videoUserService, VideoUserRepository videoUserRepository) {
        this.videoUserService = videoUserService;
        this.videoUserRepository = videoUserRepository;
    }

    /**
     * {@code POST  /video-users} : Create a new videoUser.
     *
     * @param videoUserDTO the videoUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoUserDTO, or with status {@code 400 (Bad Request)} if the videoUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/video-users")
    public ResponseEntity<VideoUserDTO> createVideoUser(@Valid @RequestBody VideoUserDTO videoUserDTO) throws URISyntaxException {
        log.debug("REST request to save VideoUser : {}", videoUserDTO);
        if (videoUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new videoUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoUserDTO result = videoUserService.save(videoUserDTO);
        return ResponseEntity
            .created(new URI("/api/video-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-users/:id} : Updates an existing videoUser.
     *
     * @param id the id of the videoUserDTO to save.
     * @param videoUserDTO the videoUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoUserDTO,
     * or with status {@code 400 (Bad Request)} if the videoUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/video-users/{id}")
    public ResponseEntity<VideoUserDTO> updateVideoUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VideoUserDTO videoUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VideoUser : {}, {}", id, videoUserDTO);
        if (videoUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VideoUserDTO result = videoUserService.update(videoUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /video-users/:id} : Partial updates given fields of an existing videoUser, field will ignore if it is null
     *
     * @param id the id of the videoUserDTO to save.
     * @param videoUserDTO the videoUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoUserDTO,
     * or with status {@code 400 (Bad Request)} if the videoUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the videoUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the videoUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/video-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VideoUserDTO> partialUpdateVideoUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VideoUserDTO videoUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VideoUser partially : {}, {}", id, videoUserDTO);
        if (videoUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VideoUserDTO> result = videoUserService.partialUpdate(videoUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /video-users} : get all the videoUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoUsers in body.
     */
    @GetMapping("/video-users")
    public List<VideoUserDTO> getAllVideoUsers() {
        log.debug("REST request to get all VideoUsers");
        return videoUserService.findAll();
    }

    /**
     * {@code GET  /video-users/:id} : get the "id" videoUser.
     *
     * @param id the id of the videoUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-users/{id}")
    public ResponseEntity<VideoUserDTO> getVideoUser(@PathVariable Long id) {
        log.debug("REST request to get VideoUser : {}", id);
        Optional<VideoUserDTO> videoUserDTO = videoUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoUserDTO);
    }

    /**
     * {@code DELETE  /video-users/:id} : delete the "id" videoUser.
     *
     * @param id the id of the videoUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-users/{id}")
    public ResponseEntity<Void> deleteVideoUser(@PathVariable Long id) {
        log.debug("REST request to delete VideoUser : {}", id);
        videoUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
