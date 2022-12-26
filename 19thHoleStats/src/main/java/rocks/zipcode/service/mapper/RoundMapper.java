package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Course;
import rocks.zipcode.domain.Golfer;
import rocks.zipcode.domain.Round;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.service.dto.CourseDTO;
import rocks.zipcode.service.dto.GolferDTO;
import rocks.zipcode.service.dto.RoundDTO;
import rocks.zipcode.service.dto.ScorecardDTO;

/**
 * Mapper for the entity {@link Round} and its DTO {@link RoundDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoundMapper extends EntityMapper<RoundDTO, Round> {
    @Mapping(target = "scorecard", source = "scorecard", qualifiedByName = "scorecardId")
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    @Mapping(target = "golfer", source = "golfer", qualifiedByName = "golferId")
    RoundDTO toDto(Round s);

    @Named("scorecardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScorecardDTO toDtoScorecardId(Scorecard scorecard);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);

    @Named("golferId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GolferDTO toDtoGolferId(Golfer golfer);
}
