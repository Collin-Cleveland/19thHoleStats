package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Round;
import rocks.zipcode.repository.RoundRepository;
import rocks.zipcode.service.dto.RoundDTO;
import rocks.zipcode.service.mapper.RoundMapper;

/**
 * Service Implementation for managing {@link Round}.
 */
@Service
@Transactional
public class RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundService.class);

    private final RoundRepository roundRepository;

    private final RoundMapper roundMapper;

    public RoundService(RoundRepository roundRepository, RoundMapper roundMapper) {
        this.roundRepository = roundRepository;
        this.roundMapper = roundMapper;
    }

    /**
     * Save a round.
     *
     * @param roundDTO the entity to save.
     * @return the persisted entity.
     */
    public RoundDTO save(RoundDTO roundDTO) {
        log.debug("Request to save Round : {}", roundDTO);
        Round round = roundMapper.toEntity(roundDTO);
        round = roundRepository.save(round);
        return roundMapper.toDto(round);
    }

    /**
     * Update a round.
     *
     * @param roundDTO the entity to save.
     * @return the persisted entity.
     */
    public RoundDTO update(RoundDTO roundDTO) {
        log.debug("Request to update Round : {}", roundDTO);
        Round round = roundMapper.toEntity(roundDTO);
        round = roundRepository.save(round);
        return roundMapper.toDto(round);
    }

    /**
     * Partially update a round.
     *
     * @param roundDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RoundDTO> partialUpdate(RoundDTO roundDTO) {
        log.debug("Request to partially update Round : {}", roundDTO);

        return roundRepository
            .findById(roundDTO.getId())
            .map(existingRound -> {
                roundMapper.partialUpdate(existingRound, roundDTO);

                return existingRound;
            })
            .map(roundRepository::save)
            .map(roundMapper::toDto);
    }

    /**
     * Get all the rounds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RoundDTO> findAll() {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll().stream().map(roundMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one round by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RoundDTO> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id).map(roundMapper::toDto);
    }

    /**
     * Delete the round by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.deleteById(id);
    }
}
