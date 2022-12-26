package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Golfer;
import rocks.zipcode.repository.GolferRepository;
import rocks.zipcode.service.dto.GolferDTO;
import rocks.zipcode.service.mapper.GolferMapper;

/**
 * Service Implementation for managing {@link Golfer}.
 */
@Service
@Transactional
public class GolferService {

    private final Logger log = LoggerFactory.getLogger(GolferService.class);

    private final GolferRepository golferRepository;

    private final GolferMapper golferMapper;

    public GolferService(GolferRepository golferRepository, GolferMapper golferMapper) {
        this.golferRepository = golferRepository;
        this.golferMapper = golferMapper;
    }

    /**
     * Save a golfer.
     *
     * @param golferDTO the entity to save.
     * @return the persisted entity.
     */
    public GolferDTO save(GolferDTO golferDTO) {
        log.debug("Request to save Golfer : {}", golferDTO);
        Golfer golfer = golferMapper.toEntity(golferDTO);
        golfer = golferRepository.save(golfer);
        return golferMapper.toDto(golfer);
    }

    /**
     * Update a golfer.
     *
     * @param golferDTO the entity to save.
     * @return the persisted entity.
     */
    public GolferDTO update(GolferDTO golferDTO) {
        log.debug("Request to update Golfer : {}", golferDTO);
        Golfer golfer = golferMapper.toEntity(golferDTO);
        golfer = golferRepository.save(golfer);
        return golferMapper.toDto(golfer);
    }

    /**
     * Partially update a golfer.
     *
     * @param golferDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GolferDTO> partialUpdate(GolferDTO golferDTO) {
        log.debug("Request to partially update Golfer : {}", golferDTO);

        return golferRepository
            .findById(golferDTO.getId())
            .map(existingGolfer -> {
                golferMapper.partialUpdate(existingGolfer, golferDTO);

                return existingGolfer;
            })
            .map(golferRepository::save)
            .map(golferMapper::toDto);
    }

    /**
     * Get all the golfers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GolferDTO> findAll() {
        log.debug("Request to get all Golfers");
        return golferRepository.findAll().stream().map(golferMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one golfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GolferDTO> findOne(Long id) {
        log.debug("Request to get Golfer : {}", id);
        return golferRepository.findById(id).map(golferMapper::toDto);
    }

    /**
     * Delete the golfer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Golfer : {}", id);
        golferRepository.deleteById(id);
    }
}
