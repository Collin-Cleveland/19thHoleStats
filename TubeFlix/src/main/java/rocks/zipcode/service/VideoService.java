package rocks.zipcode.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Video;
import rocks.zipcode.repository.VideoRepository;
import rocks.zipcode.service.dto.VideoDTO;
import rocks.zipcode.service.mapper.VideoMapper;

/**
 * Service Implementation for managing {@link Video}.
 */
@Service
@Transactional
public class VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    /**
     * Save a video.
     *
     * @param videoDTO the entity to save.
     * @return the persisted entity.
     */
    public VideoDTO save(VideoDTO videoDTO) {
        log.debug("Request to save Video : {}", videoDTO);
        Video video = videoMapper.toEntity(videoDTO);
        video = videoRepository.save(video);
        return videoMapper.toDto(video);
    }

    /**
     * Update a video.
     *
     * @param videoDTO the entity to save.
     * @return the persisted entity.
     */
    public VideoDTO update(VideoDTO videoDTO) {
        log.debug("Request to update Video : {}", videoDTO);
        Video video = videoMapper.toEntity(videoDTO);
        video = videoRepository.save(video);
        return videoMapper.toDto(video);
    }

    /**
     * Partially update a video.
     *
     * @param videoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VideoDTO> partialUpdate(VideoDTO videoDTO) {
        log.debug("Request to partially update Video : {}", videoDTO);

        return videoRepository
            .findById(videoDTO.getId())
            .map(existingVideo -> {
                videoMapper.partialUpdate(existingVideo, videoDTO);

                return existingVideo;
            })
            .map(videoRepository::save)
            .map(videoMapper::toDto);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable).map(videoMapper::toDto);
    }

    /**
     * Get one video by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoDTO> findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        return videoRepository.findById(id).map(videoMapper::toDto);
    }

    /**
     * Delete the video by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
