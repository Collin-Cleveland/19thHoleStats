package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rocks.zipcode.domain.Golfer;
import rocks.zipcode.domain.Round;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.repository.ScorecardRepository;
import rocks.zipcode.service.dto.ScorecardDTO;
import rocks.zipcode.service.mapper.ScorecardMapper;

/**
 * Service Implementation for managing {@link Scorecard}.
 */
@Service
@Transactional
public class ScorecardService {

    private final Logger log = LoggerFactory.getLogger(ScorecardService.class);

    private final ScorecardRepository scorecardRepository;

    private final ScorecardMapper scorecardMapper;

    public ScorecardService(ScorecardRepository scorecardRepository, ScorecardMapper scorecardMapper) {
        this.scorecardRepository = scorecardRepository;
        this.scorecardMapper = scorecardMapper;
    }

    /**
     * Save a scorecard.
     *
     * @param scorecardDTO the entity to save.
     * @return the persisted entity.
     */
    public ScorecardDTO save(ScorecardDTO scorecardDTO) {
        log.debug("Request to save Scorecard : {}", scorecardDTO);
        Scorecard scorecard = scorecardMapper.toEntity(scorecardDTO);
        scorecard = scorecardRepository.save(scorecard);
        return scorecardMapper.toDto(scorecard);
    }

    /**
     * Update a scorecard.
     *
     * @param scorecardDTO the entity to save.
     * @return the persisted entity.
     */
    public ScorecardDTO update(ScorecardDTO scorecardDTO) {
        log.debug("Request to update Scorecard : {}", scorecardDTO);
        Scorecard scorecard = scorecardMapper.toEntity(scorecardDTO);
        scorecard = scorecardRepository.save(scorecard);
        return scorecardMapper.toDto(scorecard);
    }

    /**
     * Partially update a scorecard.
     *
     * @param scorecardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ScorecardDTO> partialUpdate(ScorecardDTO scorecardDTO) {
        log.debug("Request to partially update Scorecard : {}", scorecardDTO);

        return scorecardRepository
            .findById(scorecardDTO.getId())
            .map(existingScorecard -> {
                scorecardMapper.partialUpdate(existingScorecard, scorecardDTO);

                return existingScorecard;
            })
            .map(scorecardRepository::save)
            .map(scorecardMapper::toDto);
    }

    /**
     * Get all the scorecards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ScorecardDTO> findAll() {
        log.debug("Request to get all Scorecards");
        return scorecardRepository.findAll().stream().map(scorecardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

//ADDED METHOD
    // @Transactional(readOnly = true)
    // public List<ScorecardDTO> findAllByGolfer(Golfer id) {
    // log.debug("Request to get all scorecards where Round is Golfer Id");
    // return StreamSupport
    //     .stream(scorecardRepository.findAll().spliterator(), false)
    //     .filter(scorecard -> scorecard.getRound(id))
    //     .map(scorecardMapper::toDto)
    //     .collect(Collectors.toCollection(LinkedList::new));
    // }

    /**
     *  Get all the scorecards where Round is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ScorecardDTO> findAllWhereRoundIsNull() {
        log.debug("Request to get all scorecards where Round is null");
        return StreamSupport
            .stream(scorecardRepository.findAll().spliterator(), false)
            .filter(scorecard -> scorecard.getRound() == null)
            .map(scorecardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one scorecard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScorecardDTO> findOne(Long id) {
        log.debug("Request to get Scorecard : {}", id);
        return scorecardRepository.findById(id).map(scorecardMapper::toDto);
    }

    /**
     * Delete the scorecard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Scorecard : {}", id);
        scorecardRepository.deleteById(id);
    }
}
