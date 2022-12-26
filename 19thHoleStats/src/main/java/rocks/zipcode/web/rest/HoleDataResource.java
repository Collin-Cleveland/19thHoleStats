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
import rocks.zipcode.repository.HoleDataRepository;
import rocks.zipcode.service.HoleDataService;
import rocks.zipcode.service.dto.HoleDataDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.HoleData}.
 */
@RestController
@RequestMapping("/api")
public class HoleDataResource {

    private final Logger log = LoggerFactory.getLogger(HoleDataResource.class);

    private static final String ENTITY_NAME = "holeData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoleDataService holeDataService;

    private final HoleDataRepository holeDataRepository;

    public HoleDataResource(HoleDataService holeDataService, HoleDataRepository holeDataRepository) {
        this.holeDataService = holeDataService;
        this.holeDataRepository = holeDataRepository;
    }

    /**
     * {@code POST  /hole-data} : Create a new holeData.
     *
     * @param holeDataDTO the holeDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holeDataDTO, or with status {@code 400 (Bad Request)} if the holeData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hole-data")
    public ResponseEntity<HoleDataDTO> createHoleData(@Valid @RequestBody HoleDataDTO holeDataDTO) throws URISyntaxException {
        log.debug("REST request to save HoleData : {}", holeDataDTO);
        if (holeDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new holeData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoleDataDTO result = holeDataService.save(holeDataDTO);
        return ResponseEntity
            .created(new URI("/api/hole-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hole-data/:id} : Updates an existing holeData.
     *
     * @param id the id of the holeDataDTO to save.
     * @param holeDataDTO the holeDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeDataDTO,
     * or with status {@code 400 (Bad Request)} if the holeDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holeDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hole-data/{id}")
    public ResponseEntity<HoleDataDTO> updateHoleData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HoleDataDTO holeDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HoleData : {}, {}", id, holeDataDTO);
        if (holeDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoleDataDTO result = holeDataService.update(holeDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hole-data/:id} : Partial updates given fields of an existing holeData, field will ignore if it is null
     *
     * @param id the id of the holeDataDTO to save.
     * @param holeDataDTO the holeDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeDataDTO,
     * or with status {@code 400 (Bad Request)} if the holeDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the holeDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the holeDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hole-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoleDataDTO> partialUpdateHoleData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HoleDataDTO holeDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoleData partially : {}, {}", id, holeDataDTO);
        if (holeDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoleDataDTO> result = holeDataService.partialUpdate(holeDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hole-data} : get all the holeData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holeData in body.
     */
    @GetMapping("/hole-data")
    public List<HoleDataDTO> getAllHoleData() {
        log.debug("REST request to get all HoleData");
        return holeDataService.findAll();
    }

    /**
     * {@code GET  /hole-data/:id} : get the "id" holeData.
     *
     * @param id the id of the holeDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holeDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hole-data/{id}")
    public ResponseEntity<HoleDataDTO> getHoleData(@PathVariable Long id) {
        log.debug("REST request to get HoleData : {}", id);
        Optional<HoleDataDTO> holeDataDTO = holeDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holeDataDTO);
    }

    /**
     * {@code DELETE  /hole-data/:id} : delete the "id" holeData.
     *
     * @param id the id of the holeDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hole-data/{id}")
    public ResponseEntity<Void> deleteHoleData(@PathVariable Long id) {
        log.debug("REST request to delete HoleData : {}", id);
        holeDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
