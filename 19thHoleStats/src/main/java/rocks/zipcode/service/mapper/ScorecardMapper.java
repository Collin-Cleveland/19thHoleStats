package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Course;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.service.dto.CourseDTO;
import rocks.zipcode.service.dto.ScorecardDTO;

/**
 * Mapper for the entity {@link Scorecard} and its DTO {@link ScorecardDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScorecardMapper extends EntityMapper<ScorecardDTO, Scorecard> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    ScorecardDTO toDto(Scorecard s);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);
}
