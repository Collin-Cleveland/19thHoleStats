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
import rocks.zipcode.repository.GolferRepository;
import rocks.zipcode.service.GolferService;
import rocks.zipcode.service.dto.GolferDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Golfer}.
 */
@RestController
@RequestMapping("/api")
public class GolferResource {

    private final Logger log = LoggerFactory.getLogger(GolferResource.class);

    private static final String ENTITY_NAME = "golfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GolferService golferService;

    private final GolferRepository golferRepository;

    public GolferResource(GolferService golferService, GolferRepository golferRepository) {
        this.golferService = golferService;
        this.golferRepository = golferRepository;
    }

    /**
     * {@code POST  /golfers} : Create a new golfer.
     *
     * @param golferDTO the golferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new golferDTO, or with status {@code 400 (Bad Request)} if the golfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/golfers")
    public ResponseEntity<GolferDTO> createGolfer(@Valid @RequestBody GolferDTO golferDTO) throws URISyntaxException {
        log.debug("REST request to save Golfer : {}", golferDTO);
        if (golferDTO.getId() != null) {
            throw new BadRequestAlertException("A new golfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GolferDTO result = golferService.save(golferDTO);
        return ResponseEntity
            .created(new URI("/api/golfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /golfers/:id} : Updates an existing golfer.
     *
     * @param id the id of the golferDTO to save.
     * @param golferDTO the golferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golferDTO,
     * or with status {@code 400 (Bad Request)} if the golferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the golferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/golfers/{id}")
    public ResponseEntity<GolferDTO> updateGolfer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GolferDTO golferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Golfer : {}, {}", id, golferDTO);
        if (golferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GolferDTO result = golferService.update(golferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /golfers/:id} : Partial updates given fields of an existing golfer, field will ignore if it is null
     *
     * @param id the id of the golferDTO to save.
     * @param golferDTO the golferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golferDTO,
     * or with status {@code 400 (Bad Request)} if the golferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the golferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the golferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/golfers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GolferDTO> partialUpdateGolfer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GolferDTO golferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Golfer partially : {}, {}", id, golferDTO);
        if (golferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GolferDTO> result = golferService.partialUpdate(golferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /golfers} : get all the golfers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of golfers in body.
     */
    @GetMapping("/golfers")
    public List<GolferDTO> getAllGolfers() {
        log.debug("REST request to get all Golfers");
        return golferService.findAll();
    }

    /**
     * {@code GET  /golfers/:id} : get the "id" golfer.
     *
     * @param id the id of the golferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the golferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/golfers/{id}")
    public ResponseEntity<GolferDTO> getGolfer(@PathVariable Long id) {
        log.debug("REST request to get Golfer : {}", id);
        Optional<GolferDTO> golferDTO = golferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(golferDTO);
    }

    /**
     * {@code DELETE  /golfers/:id} : delete the "id" golfer.
     *
     * @param id the id of the golferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/golfers/{id}")
    public ResponseEntity<Void> deleteGolfer(@PathVariable Long id) {
        log.debug("REST request to delete Golfer : {}", id);
        golferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
