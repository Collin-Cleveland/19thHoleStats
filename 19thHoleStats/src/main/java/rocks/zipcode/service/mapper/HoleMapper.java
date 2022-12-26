package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Course;
import rocks.zipcode.domain.Hole;
import rocks.zipcode.service.dto.CourseDTO;
import rocks.zipcode.service.dto.HoleDTO;

/**
 * Mapper for the entity {@link Hole} and its DTO {@link HoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface HoleMapper extends EntityMapper<HoleDTO, Hole> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    HoleDTO toDto(Hole s);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);
}
