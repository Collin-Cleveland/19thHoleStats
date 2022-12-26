package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.HoleRepository;
import rocks.zipcode.service.HoleService;
import rocks.zipcode.service.dto.HoleDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Hole}.
 */
@RestController
@RequestMapping("/api")
public class HoleResource {

    private final Logger log = LoggerFactory.getLogger(HoleResource.class);

    private static final String ENTITY_NAME = "hole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoleService holeService;

    private final HoleRepository holeRepository;

    public HoleResource(HoleService holeService, HoleRepository holeRepository) {
        this.holeService = holeService;
        this.holeRepository = holeRepository;
    }

    /**
     * {@code POST  /holes} : Create a new hole.
     *
     * @param holeDTO the holeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holeDTO, or with status {@code 400 (Bad Request)} if the hole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holes")
    public ResponseEntity<HoleDTO> createHole(@Valid @RequestBody HoleDTO holeDTO) throws URISyntaxException {
        log.debug("REST request to save Hole : {}", holeDTO);
        if (holeDTO.getId() != null) {
            throw new BadRequestAlertException("A new hole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoleDTO result = holeService.save(holeDTO);
        return ResponseEntity
            .created(new URI("/api/holes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holes/:id} : Updates an existing hole.
     *
     * @param id the id of the holeDTO to save.
     * @param holeDTO the holeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeDTO,
     * or with status {@code 400 (Bad Request)} if the holeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holes/{id}")
    public ResponseEntity<HoleDTO> updateHole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HoleDTO holeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Hole : {}, {}", id, holeDTO);
        if (holeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoleDTO result = holeService.update(holeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /holes/:id} : Partial updates given fields of an existing hole, field will ignore if it is null
     *
     * @param id the id of the holeDTO to save.
     * @param holeDTO the holeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeDTO,
     * or with status {@code 400 (Bad Request)} if the holeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the holeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the holeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/holes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoleDTO> partialUpdateHole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HoleDTO holeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hole partially : {}, {}", id, holeDTO);
        if (holeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoleDTO> result = holeService.partialUpdate(holeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /holes} : get all the holes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holes in body.
     */
    @GetMapping("/holes")
    public List<HoleDTO> getAllHoles(@RequestParam(required = false) String filter) {
        if ("holedata-is-null".equals(filter)) {
            log.debug("REST request to get all Holes where holeData is null");
            return holeService.findAllWhereHoleDataIsNull();
        }
        log.debug("REST request to get all Holes");
        return holeService.findAll();
    }

    /**
     * {@code GET  /holes/:id} : get the "id" hole.
     *
     * @param id the id of the holeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holes/{id}")
    public ResponseEntity<HoleDTO> getHole(@PathVariable Long id) {
        log.debug("REST request to get Hole : {}", id);
        Optional<HoleDTO> holeDTO = holeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holeDTO);
    }

    /**
     * {@code DELETE  /holes/:id} : delete the "id" hole.
     *
     * @param id the id of the holeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holes/{id}")
    public ResponseEntity<Void> deleteHole(@PathVariable Long id) {
        log.debug("REST request to delete Hole : {}", id);
        holeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
