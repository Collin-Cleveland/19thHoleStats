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
import rocks.zipcode.domain.Course;
import rocks.zipcode.repository.CourseRepository;
import rocks.zipcode.service.dto.CourseDTO;
import rocks.zipcode.service.mapper.CourseMapper;

/**
 * Service Implementation for managing {@link Course}.
 */
@Service
@Transactional
public class CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    public CourseDTO save(CourseDTO courseDTO) {
        log.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * Update a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    public CourseDTO update(CourseDTO courseDTO) {
        log.debug("Request to update Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * Partially update a course.
     *
     * @param courseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CourseDTO> partialUpdate(CourseDTO courseDTO) {
        log.debug("Request to partially update Course : {}", courseDTO);

        return courseRepository
            .findById(courseDTO.getId())
            .map(existingCourse -> {
                courseMapper.partialUpdate(existingCourse, courseDTO);

                return existingCourse;
            })
            .map(courseRepository::save)
            .map(courseMapper::toDto);
    }

    /**
     * Get all the courses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CourseDTO> findAll() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll().stream().map(courseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the courses where Scorecard is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CourseDTO> findAllWhereScorecardIsNull() {
        log.debug("Request to get all courses where Scorecard is null");
        return StreamSupport
            .stream(courseRepository.findAll().spliterator(), false)
            .filter(course -> course.getScorecard() == null)
            .map(courseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one course by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    /**
     * Delete the course by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
