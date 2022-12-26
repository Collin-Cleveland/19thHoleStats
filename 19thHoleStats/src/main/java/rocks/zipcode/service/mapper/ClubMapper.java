package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Club;
import rocks.zipcode.service.dto.ClubDTO;

/**
 * Mapper for the entity {@link Club} and its DTO {@link ClubDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClubMapper extends EntityMapper<ClubDTO, Club> {}
