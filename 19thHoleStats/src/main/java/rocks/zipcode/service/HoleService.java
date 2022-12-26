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
import rocks.zipcode.domain.Hole;
import rocks.zipcode.repository.HoleRepository;
import rocks.zipcode.service.dto.HoleDTO;
import rocks.zipcode.service.mapper.HoleMapper;

/**
 * Service Implementation for managing {@link Hole}.
 */
@Service
@Transactional
public class HoleService {

    private final Logger log = LoggerFactory.getLogger(HoleService.class);

    private final HoleRepository holeRepository;

    private final HoleMapper holeMapper;

    public HoleService(HoleRepository holeRepository, HoleMapper holeMapper) {
        this.holeRepository = holeRepository;
        this.holeMapper = holeMapper;
    }

    /**
     * Save a hole.
     *
     * @param holeDTO the entity to save.
     * @return the persisted entity.
     */
    public HoleDTO save(HoleDTO holeDTO) {
        log.debug("Request to save Hole : {}", holeDTO);
        Hole hole = holeMapper.toEntity(holeDTO);
        hole = holeRepository.save(hole);
        return holeMapper.toDto(hole);
    }

    /**
     * Update a hole.
     *
     * @param holeDTO the entity to save.
     * @return the persisted entity.
     */
    public HoleDTO update(HoleDTO holeDTO) {
        log.debug("Request to update Hole : {}", holeDTO);
        Hole hole = holeMapper.toEntity(holeDTO);
        hole = holeRepository.save(hole);
        return holeMapper.toDto(hole);
    }

    /**
     * Partially update a hole.
     *
     * @param holeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HoleDTO> partialUpdate(HoleDTO holeDTO) {
        log.debug("Request to partially update Hole : {}", holeDTO);

        return holeRepository
            .findById(holeDTO.getId())
            .map(existingHole -> {
                holeMapper.partialUpdate(existingHole, holeDTO);

                return existingHole;
            })
            .map(holeRepository::save)
            .map(holeMapper::toDto);
    }

    /**
     * Get all the holes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HoleDTO> findAll() {
        log.debug("Request to get all Holes");
        return holeRepository.findAll().stream().map(holeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the holes where HoleData is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HoleDTO> findAllWhereHoleDataIsNull() {
        log.debug("Request to get all holes where HoleData is null");
        return StreamSupport
            .stream(holeRepository.findAll().spliterator(), false)
            .filter(hole -> hole.getHoleData() == null)
            .map(holeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HoleDTO> findOne(Long id) {
        log.debug("Request to get Hole : {}", id);
        return holeRepository.findById(id).map(holeMapper::toDto);
    }

    /**
     * Delete the hole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hole : {}", id);
        holeRepository.deleteById(id);
    }
}
