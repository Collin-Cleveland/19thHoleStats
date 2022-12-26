package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Club;
import rocks.zipcode.domain.Course;
import rocks.zipcode.service.dto.ClubDTO;
import rocks.zipcode.service.dto.CourseDTO;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Mapping(target = "club", source = "club", qualifiedByName = "clubId")
    CourseDTO toDto(Course s);

    @Named("clubId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClubDTO toDtoClubId(Club club);
}
