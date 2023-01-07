package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.repository.VideoUserRepository;
import rocks.zipcode.service.dto.VideoUserDTO;
import rocks.zipcode.service.mapper.VideoUserMapper;

/**
 * Service Implementation for managing {@link VideoUser}.
 */
@Service
@Transactional
public class VideoUserService {

    private final Logger log = LoggerFactory.getLogger(VideoUserService.class);

    private final VideoUserRepository videoUserRepository;

    private final VideoUserMapper videoUserMapper;

    public VideoUserService(VideoUserRepository videoUserRepository, VideoUserMapper videoUserMapper) {
        this.videoUserRepository = videoUserRepository;
        this.videoUserMapper = videoUserMapper;
    }

    /**
     * Save a videoUser.
     *
     * @param videoUserDTO the entity to save.
     * @return the persisted entity.
     */
    public VideoUserDTO save(VideoUserDTO videoUserDTO) {
        log.debug("Request to save VideoUser : {}", videoUserDTO);
        VideoUser videoUser = videoUserMapper.toEntity(videoUserDTO);
        videoUser = videoUserRepository.save(videoUser);
        return videoUserMapper.toDto(videoUser);
    }

    /**
     * Update a videoUser.
     *
     * @param videoUserDTO the entity to save.
     * @return the persisted entity.
     */
    public VideoUserDTO update(VideoUserDTO videoUserDTO) {
        log.debug("Request to update VideoUser : {}", videoUserDTO);
        VideoUser videoUser = videoUserMapper.toEntity(videoUserDTO);
        videoUser = videoUserRepository.save(videoUser);
        return videoUserMapper.toDto(videoUser);
    }

    /**
     * Partially update a videoUser.
     *
     * @param videoUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VideoUserDTO> partialUpdate(VideoUserDTO videoUserDTO) {
        log.debug("Request to partially update VideoUser : {}", videoUserDTO);

        return videoUserRepository
            .findById(videoUserDTO.getId())
            .map(existingVideoUser -> {
                videoUserMapper.partialUpdate(existingVideoUser, videoUserDTO);

                return existingVideoUser;
            })
            .map(videoUserRepository::save)
            .map(videoUserMapper::toDto);
    }

    /**
     * Get all the videoUsers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VideoUserDTO> findAll() {
        log.debug("Request to get all VideoUsers");
        return videoUserRepository.findAll().stream().map(videoUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one videoUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoUserDTO> findOne(Long id) {
        log.debug("Request to get VideoUser : {}", id);
        return videoUserRepository.findById(id).map(videoUserMapper::toDto);
    }

    /**
     * Delete the videoUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoUser : {}", id);
        videoUserRepository.deleteById(id);
    }
}
