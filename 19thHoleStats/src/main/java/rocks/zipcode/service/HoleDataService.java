package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.HoleData;
import rocks.zipcode.repository.HoleDataRepository;
import rocks.zipcode.service.dto.HoleDataDTO;
import rocks.zipcode.service.mapper.HoleDataMapper;

/**
 * Service Implementation for managing {@link HoleData}.
 */
@Service
@Transactional
public class HoleDataService {

    private final Logger log = LoggerFactory.getLogger(HoleDataService.class);

    private final HoleDataRepository holeDataRepository;

    private final HoleDataMapper holeDataMapper;

    public HoleDataService(HoleDataRepository holeDataRepository, HoleDataMapper holeDataMapper) {
        this.holeDataRepository = holeDataRepository;
        this.holeDataMapper = holeDataMapper;
    }

    /**
     * Save a holeData.
     *
     * @param holeDataDTO the entity to save.
     * @return the persisted entity.
     */
    public HoleDataDTO save(HoleDataDTO holeDataDTO) {
        log.debug("Request to save HoleData : {}", holeDataDTO);
        HoleData holeData = holeDataMapper.toEntity(holeDataDTO);
        holeData = holeDataRepository.save(holeData);
        return holeDataMapper.toDto(holeData);
    }

    /**
     * Update a holeData.
     *
     * @param holeDataDTO the entity to save.
     * @return the persisted entity.
     */
    public HoleDataDTO update(HoleDataDTO holeDataDTO) {
        log.debug("Request to update HoleData : {}", holeDataDTO);
        HoleData holeData = holeDataMapper.toEntity(holeDataDTO);
        holeData = holeDataRepository.save(holeData);
        return holeDataMapper.toDto(holeData);
    }

    /**
     * Partially update a holeData.
     *
     * @param holeDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HoleDataDTO> partialUpdate(HoleDataDTO holeDataDTO) {
        log.debug("Request to partially update HoleData : {}", holeDataDTO);

        return holeDataRepository
            .findById(holeDataDTO.getId())
            .map(existingHoleData -> {
                holeDataMapper.partialUpdate(existingHoleData, holeDataDTO);

                return existingHoleData;
            })
            .map(holeDataRepository::save)
            .map(holeDataMapper::toDto);
    }

    /**
     * Get all the holeData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HoleDataDTO> findAll() {
        log.debug("Request to get all HoleData");
        return holeDataRepository.findAll().stream().map(holeDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one holeData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HoleDataDTO> findOne(Long id) {
        log.debug("Request to get HoleData : {}", id);
        return holeDataRepository.findById(id).map(holeDataMapper::toDto);
    }

    /**
     * Delete the holeData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HoleData : {}", id);
        holeDataRepository.deleteById(id);
    }
}
