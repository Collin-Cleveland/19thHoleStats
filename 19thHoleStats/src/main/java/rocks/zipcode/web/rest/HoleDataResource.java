package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.domain.HoleData;
import rocks.zipcode.repository.HoleDataRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.HoleData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HoleDataResource {

    private final Logger log = LoggerFactory.getLogger(HoleDataResource.class);

    private static final String ENTITY_NAME = "holeData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoleDataRepository holeDataRepository;

    public HoleDataResource(HoleDataRepository holeDataRepository) {
        this.holeDataRepository = holeDataRepository;
    }

    /**
     * {@code POST  /hole-data} : Create a new holeData.
     *
     * @param holeData the holeData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holeData, or with status {@code 400 (Bad Request)} if the holeData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hole-data")
    public ResponseEntity<HoleData> createHoleData(@RequestBody HoleData holeData) throws URISyntaxException {
        log.debug("REST request to save HoleData : {}", holeData);
        if (holeData.getId() != null) {
            throw new BadRequestAlertException("A new holeData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoleData result = holeDataRepository.save(holeData);
        return ResponseEntity
            .created(new URI("/api/hole-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hole-data/:id} : Updates an existing holeData.
     *
     * @param id the id of the holeData to save.
     * @param holeData the holeData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeData,
     * or with status {@code 400 (Bad Request)} if the holeData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holeData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hole-data/{id}")
    public ResponseEntity<HoleData> updateHoleData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HoleData holeData
    ) throws URISyntaxException {
        log.debug("REST request to update HoleData : {}, {}", id, holeData);
        if (holeData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoleData result = holeDataRepository.save(holeData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hole-data/:id} : Partial updates given fields of an existing holeData, field will ignore if it is null
     *
     * @param id the id of the holeData to save.
     * @param holeData the holeData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holeData,
     * or with status {@code 400 (Bad Request)} if the holeData is not valid,
     * or with status {@code 404 (Not Found)} if the holeData is not found,
     * or with status {@code 500 (Internal Server Error)} if the holeData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hole-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoleData> partialUpdateHoleData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HoleData holeData
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoleData partially : {}, {}", id, holeData);
        if (holeData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holeData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holeDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoleData> result = holeDataRepository
            .findById(holeData.getId())
            .map(existingHoleData -> {
                if (holeData.getHoleScore() != null) {
                    existingHoleData.setHoleScore(holeData.getHoleScore());
                }
                if (holeData.getPutts() != null) {
                    existingHoleData.setPutts(holeData.getPutts());
                }
                if (holeData.getFairwayHit() != null) {
                    existingHoleData.setFairwayHit(holeData.getFairwayHit());
                }

                return existingHoleData;
            })
            .map(holeDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holeData.getId().toString())
        );
    }

    /**
     * {@code GET  /hole-data} : get all the holeData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holeData in body.
     */
    @GetMapping("/hole-data")
    public List<HoleData> getAllHoleData() {
        log.debug("REST request to get all HoleData");
        return holeDataRepository.findAll();
    }

    /**
     * {@code GET  /hole-data/:id} : get the "id" holeData.
     *
     * @param id the id of the holeData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holeData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hole-data/{id}")
    public ResponseEntity<HoleData> getHoleData(@PathVariable Long id) {
        log.debug("REST request to get HoleData : {}", id);
        Optional<HoleData> holeData = holeDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(holeData);
    }

    /**
     * {@code DELETE  /hole-data/:id} : delete the "id" holeData.
     *
     * @param id the id of the holeData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hole-data/{id}")
    public ResponseEntity<Void> deleteHoleData(@PathVariable Long id) {
        log.debug("REST request to delete HoleData : {}", id);
        holeDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
