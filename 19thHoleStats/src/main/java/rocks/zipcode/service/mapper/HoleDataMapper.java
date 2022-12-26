package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Hole;
import rocks.zipcode.domain.HoleData;
import rocks.zipcode.domain.Scorecard;
import rocks.zipcode.service.dto.HoleDTO;
import rocks.zipcode.service.dto.HoleDataDTO;
import rocks.zipcode.service.dto.ScorecardDTO;

/**
 * Mapper for the entity {@link HoleData} and its DTO {@link HoleDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface HoleDataMapper extends EntityMapper<HoleDataDTO, HoleData> {
    @Mapping(target = "hole", source = "hole", qualifiedByName = "holeId")
    @Mapping(target = "scorecard", source = "scorecard", qualifiedByName = "scorecardId")
    HoleDataDTO toDto(HoleData s);

    @Named("holeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HoleDTO toDtoHoleId(Hole hole);

    @Named("scorecardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScorecardDTO toDtoScorecardId(Scorecard scorecard);
}
