package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.ScorecardRepository;
import rocks.zipcode.service.ScorecardService;
import rocks.zipcode.service.dto.ScorecardDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Scorecard}.
 */
@RestController
@RequestMapping("/api")
public class ScorecardResource {

    private final Logger log = LoggerFactory.getLogger(ScorecardResource.class);

    private static final String ENTITY_NAME = "scorecard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScorecardService scorecardService;

    private final ScorecardRepository scorecardRepository;

    public ScorecardResource(ScorecardService scorecardService, ScorecardRepository scorecardRepository) {
        this.scorecardService = scorecardService;
        this.scorecardRepository = scorecardRepository;
    }

    /**
     * {@code POST  /scorecards} : Create a new scorecard.
     *
     * @param scorecardDTO the scorecardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scorecardDTO, or with status {@code 400 (Bad Request)} if the scorecard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scorecards")
    public ResponseEntity<ScorecardDTO> createScorecard(@RequestBody ScorecardDTO scorecardDTO) throws URISyntaxException {
        log.debug("REST request to save Scorecard : {}", scorecardDTO);
        if (scorecardDTO.getId() != null) {
            throw new BadRequestAlertException("A new scorecard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScorecardDTO result = scorecardService.save(scorecardDTO);
        return ResponseEntity
            .created(new URI("/api/scorecards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scorecards/:id} : Updates an existing scorecard.
     *
     * @param id the id of the scorecardDTO to save.
     * @param scorecardDTO the scorecardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scorecardDTO,
     * or with status {@code 400 (Bad Request)} if the scorecardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scorecardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scorecards/{id}")
    public ResponseEntity<ScorecardDTO> updateScorecard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScorecardDTO scorecardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Scorecard : {}, {}", id, scorecardDTO);
        if (scorecardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scorecardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scorecardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScorecardDTO result = scorecardService.update(scorecardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scorecardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scorecards/:id} : Partial updates given fields of an existing scorecard, field will ignore if it is null
     *
     * @param id the id of the scorecardDTO to save.
     * @param scorecardDTO the scorecardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scorecardDTO,
     * or with status {@code 400 (Bad Request)} if the scorecardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the scorecardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the scorecardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scorecards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScorecardDTO> partialUpdateScorecard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScorecardDTO scorecardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scorecard partially : {}, {}", id, scorecardDTO);
        if (scorecardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scorecardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scorecardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScorecardDTO> result = scorecardService.partialUpdate(scorecardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scorecardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /scorecards} : get all the scorecards.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scorecards in body.
     */
    @GetMapping("/scorecards")
    public List<ScorecardDTO> getAllScorecards(@RequestParam(required = false) String filter) {
        if ("round-is-null".equals(filter)) {
            log.debug("REST request to get all Scorecards where round is null");
            return scorecardService.findAllWhereRoundIsNull();
        }
        log.debug("REST request to get all Scorecards");
        return scorecardService.findAll();
    }

    /**
     * {@code GET  /scorecards/:id} : get the "id" scorecard.
     *
     * @param id the id of the scorecardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scorecardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scorecards/{id}")
    public ResponseEntity<ScorecardDTO> getScorecard(@PathVariable Long id) {
        log.debug("REST request to get Scorecard : {}", id);
        Optional<ScorecardDTO> scorecardDTO = scorecardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scorecardDTO);
    }

    /**
     * {@code DELETE  /scorecards/:id} : delete the "id" scorecard.
     *
     * @param id the id of the scorecardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scorecards/{id}")
    public ResponseEntity<Void> deleteScorecard(@PathVariable Long id) {
        log.debug("REST request to delete Scorecard : {}", id);
        scorecardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
