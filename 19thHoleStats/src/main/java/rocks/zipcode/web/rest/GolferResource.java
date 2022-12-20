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
import rocks.zipcode.domain.Golfer;
import rocks.zipcode.repository.GolferRepository;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.Golfer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GolferResource {

    private final Logger log = LoggerFactory.getLogger(GolferResource.class);

    private static final String ENTITY_NAME = "golfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GolferRepository golferRepository;

    public GolferResource(GolferRepository golferRepository) {
        this.golferRepository = golferRepository;
    }

    /**
     * {@code POST  /golfers} : Create a new golfer.
     *
     * @param golfer the golfer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new golfer, or with status {@code 400 (Bad Request)} if the golfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/golfers")
    public ResponseEntity<Golfer> createGolfer(@RequestBody Golfer golfer) throws URISyntaxException {
        log.debug("REST request to save Golfer : {}", golfer);
        if (golfer.getId() != null) {
            throw new BadRequestAlertException("A new golfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Golfer result = golferRepository.save(golfer);
        return ResponseEntity
            .created(new URI("/api/golfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /golfers/:id} : Updates an existing golfer.
     *
     * @param id the id of the golfer to save.
     * @param golfer the golfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golfer,
     * or with status {@code 400 (Bad Request)} if the golfer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the golfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/golfers/{id}")
    public ResponseEntity<Golfer> updateGolfer(@PathVariable(value = "id", required = false) final Long id, @RequestBody Golfer golfer)
        throws URISyntaxException {
        log.debug("REST request to update Golfer : {}, {}", id, golfer);
        if (golfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golfer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Golfer result = golferRepository.save(golfer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golfer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /golfers/:id} : Partial updates given fields of an existing golfer, field will ignore if it is null
     *
     * @param id the id of the golfer to save.
     * @param golfer the golfer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated golfer,
     * or with status {@code 400 (Bad Request)} if the golfer is not valid,
     * or with status {@code 404 (Not Found)} if the golfer is not found,
     * or with status {@code 500 (Internal Server Error)} if the golfer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/golfers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Golfer> partialUpdateGolfer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Golfer golfer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Golfer partially : {}, {}", id, golfer);
        if (golfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, golfer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!golferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Golfer> result = golferRepository
            .findById(golfer.getId())
            .map(existingGolfer -> {
                if (golfer.getName() != null) {
                    existingGolfer.setName(golfer.getName());
                }
                if (golfer.getAvgScore() != null) {
                    existingGolfer.setAvgScore(golfer.getAvgScore());
                }
                if (golfer.getRoundsPlayed() != null) {
                    existingGolfer.setRoundsPlayed(golfer.getRoundsPlayed());
                }
                if (golfer.getHandicap() != null) {
                    existingGolfer.setHandicap(golfer.getHandicap());
                }

                return existingGolfer;
            })
            .map(golferRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, golfer.getId().toString())
        );
    }

    /**
     * {@code GET  /golfers} : get all the golfers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of golfers in body.
     */
    @GetMapping("/golfers")
    public List<Golfer> getAllGolfers() {
        log.debug("REST request to get all Golfers");
        return golferRepository.findAll();
    }

    /**
     * {@code GET  /golfers/:id} : get the "id" golfer.
     *
     * @param id the id of the golfer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the golfer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/golfers/{id}")
    public ResponseEntity<Golfer> getGolfer(@PathVariable Long id) {
        log.debug("REST request to get Golfer : {}", id);
        Optional<Golfer> golfer = golferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(golfer);
    }

    /**
     * {@code DELETE  /golfers/:id} : delete the "id" golfer.
     *
     * @param id the id of the golfer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/golfers/{id}")
    public ResponseEntity<Void> deleteGolfer(@PathVariable Long id) {
        log.debug("REST request to delete Golfer : {}", id);
        golferRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
