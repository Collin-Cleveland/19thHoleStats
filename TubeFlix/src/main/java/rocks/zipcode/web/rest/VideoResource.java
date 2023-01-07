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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rocks.zipcode.repository.VideoRepository;
import rocks.zipcode.service.VideoService;
import rocks.zipcode.service.dto.VideoDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Video}.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoService videoService;

    private final VideoRepository videoRepository;

    public VideoResource(VideoService videoService, VideoRepository videoRepository) {
        this.videoService = videoService;
        this.videoRepository = videoRepository;
    }

    /**
     * {@code POST  /videos} : Create a new video.
     *
     * @param videoDTO the videoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoDTO, or with status {@code 400 (Bad Request)} if the video has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/videos")
    public ResponseEntity<VideoDTO> createVideo(@Valid @RequestBody VideoDTO videoDTO) throws URISyntaxException {
        log.debug("REST request to save Video : {}", videoDTO);
        if (videoDTO.getId() != null) {
            throw new BadRequestAlertException("A new video cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoDTO result = videoService.save(videoDTO);
        return ResponseEntity
            .created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /videos/:id} : Updates an existing video.
     *
     * @param id the id of the videoDTO to save.
     * @param videoDTO the videoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoDTO,
     * or with status {@code 400 (Bad Request)} if the videoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> updateVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VideoDTO videoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Video : {}, {}", id, videoDTO);
        if (videoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VideoDTO result = videoService.update(videoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /videos/:id} : Partial updates given fields of an existing video, field will ignore if it is null
     *
     * @param id the id of the videoDTO to save.
     * @param videoDTO the videoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoDTO,
     * or with status {@code 400 (Bad Request)} if the videoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the videoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the videoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/videos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VideoDTO> partialUpdateVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VideoDTO videoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Video partially : {}, {}", id, videoDTO);
        if (videoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, videoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!videoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VideoDTO> result = videoService.partialUpdate(videoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, videoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /videos} : get all the videos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videos in body.
     */
    @GetMapping("/videos")
    public ResponseEntity<List<VideoDTO>> getAllVideos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Videos");
        Page<VideoDTO> page = videoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /videos/:id} : get the "id" video.
     *
     * @param id the id of the videoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        Optional<VideoDTO> videoDTO = videoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoDTO);
    }

    /**
     * {@code DELETE  /videos/:id} : delete the "id" video.
     *
     * @param id the id of the videoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
