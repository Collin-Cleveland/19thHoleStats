package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.repository.ScorecardRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Scorecard}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScorecardResource {

    private final Logger log = LoggerFactory.getLogger(ScorecardResource.class);

    private static final String ENTITY_NAME = "scorecard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScorecardRepository scorecardRepository;

    public ScorecardResource(ScorecardRepository scorecardRepository) {
        this.scorecardRepository = scorecardRepository;
    }

    /**
     * {@code POST  /scorecards} : Create a new scorecard.
     *
     * @param scorecard the scorecard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scorecard, or with status {@code 400 (Bad Request)} if the scorecard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scorecards")
    public ResponseEntity<Scorecard> createScorecard(@RequestBody Scorecard scorecard) throws URISyntaxException {
        log.debug("REST request to save Scorecard : {}", scorecard);
        if (scorecard.getId() != null) {
            throw new BadRequestAlertException("A new scorecard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scorecard result = scorecardRepository.save(scorecard);
        return ResponseEntity
            .created(new URI("/api/scorecards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scorecards/:id} : Updates an existing scorecard.
     *
     * @param id the id of the scorecard to save.
     * @param scorecard the scorecard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scorecard,
     * or with status {@code 400 (Bad Request)} if the scorecard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scorecard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scorecards/{id}")
    public ResponseEntity<Scorecard> updateScorecard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Scorecard scorecard
    ) throws URISyntaxException {
        log.debug("REST request to update Scorecard : {}, {}", id, scorecard);
        if (scorecard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scorecard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scorecardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Scorecard result = scorecardRepository.save(scorecard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scorecard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scorecards/:id} : Partial updates given fields of an existing scorecard, field will ignore if it is null
     *
     * @param id the id of the scorecard to save.
     * @param scorecard the scorecard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scorecard,
     * or with status {@code 400 (Bad Request)} if the scorecard is not valid,
     * or with status {@code 404 (Not Found)} if the scorecard is not found,
     * or with status {@code 500 (Internal Server Error)} if the scorecard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scorecards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Scorecard> partialUpdateScorecard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Scorecard scorecard
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scorecard partially : {}, {}", id, scorecard);
        if (scorecard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scorecard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scorecardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Scorecard> result = scorecardRepository
            .findById(scorecard.getId())
            .map(existingScorecard -> {
                if (scorecard.getTeeColor() != null) {
                    existingScorecard.setTeeColor(scorecard.getTeeColor());
                }
                if (scorecard.getTotalScore() != null) {
                    existingScorecard.setTotalScore(scorecard.getTotalScore());
                }
                if (scorecard.getTotalPutts() != null) {
                    existingScorecard.setTotalPutts(scorecard.getTotalPutts());
                }
                if (scorecard.getFairwaysHit() != null) {
                    existingScorecard.setFairwaysHit(scorecard.getFairwaysHit());
                }

                return existingScorecard;
            })
            .map(scorecardRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scorecard.getId().toString())
        );
    }

    /**
     * {@code GET  /scorecards} : get all the scorecards.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scorecards in body.
     */
    @GetMapping("/scorecards")
    public List<Scorecard> getAllScorecards(@RequestParam(required = false) String filter) {
        if ("round-is-null".equals(filter)) {
            log.debug("REST request to get all Scorecards where round is null");
            return StreamSupport
                .stream(scorecardRepository.findAll().spliterator(), false)
                .filter(scorecard -> scorecard.getRound() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Scorecards");
        return scorecardRepository.findAll();
    }

    /**
     * {@code GET  /scorecards/:id} : get the "id" scorecard.
     *
     * @param id the id of the scorecard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scorecard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scorecards/{id}")
    public ResponseEntity<Scorecard> getScorecard(@PathVariable Long id) {
        log.debug("REST request to get Scorecard : {}", id);
        Optional<Scorecard> scorecard = scorecardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scorecard);
    }

    /**
     * {@code DELETE  /scorecards/:id} : delete the "id" scorecard.
     *
     * @param id the id of the scorecard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scorecards/{id}")
    public ResponseEntity<Void> deleteScorecard(@PathVariable Long id) {
        log.debug("REST request to delete Scorecard : {}", id);
        scorecardRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
