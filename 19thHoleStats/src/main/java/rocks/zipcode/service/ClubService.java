package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Club;
import rocks.zipcode.repository.ClubRepository;
import rocks.zipcode.service.dto.ClubDTO;
import rocks.zipcode.service.mapper.ClubMapper;

/**
 * Service Implementation for managing {@link Club}.
 */
@Service
@Transactional
public class ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubService.class);

    private final ClubRepository clubRepository;

    private final ClubMapper clubMapper;

    public ClubService(ClubRepository clubRepository, ClubMapper clubMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
    }

    /**
     * Save a club.
     *
     * @param clubDTO the entity to save.
     * @return the persisted entity.
     */
    public ClubDTO save(ClubDTO clubDTO) {
        log.debug("Request to save Club : {}", clubDTO);
        Club club = clubMapper.toEntity(clubDTO);
        club = clubRepository.save(club);
        return clubMapper.toDto(club);
    }

    /**
     * Update a club.
     *
     * @param clubDTO the entity to save.
     * @return the persisted entity.
     */
    public ClubDTO update(ClubDTO clubDTO) {
        log.debug("Request to update Club : {}", clubDTO);
        Club club = clubMapper.toEntity(clubDTO);
        club = clubRepository.save(club);
        return clubMapper.toDto(club);
    }

    /**
     * Partially update a club.
     *
     * @param clubDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClubDTO> partialUpdate(ClubDTO clubDTO) {
        log.debug("Request to partially update Club : {}", clubDTO);

        return clubRepository
            .findById(clubDTO.getId())
            .map(existingClub -> {
                clubMapper.partialUpdate(existingClub, clubDTO);

                return existingClub;
            })
            .map(clubRepository::save)
            .map(clubMapper::toDto);
    }

    /**
     * Get all the clubs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClubDTO> findAll() {
        log.debug("Request to get all Clubs");
        return clubRepository.findAll().stream().map(clubMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one club by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClubDTO> findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        return clubRepository.findById(id).map(clubMapper::toDto);
    }

    /**
     * Delete the club by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        clubRepository.deleteById(id);
    }
}
