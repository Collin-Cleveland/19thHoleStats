package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Golfer;
import rocks.zipcode.domain.User;
import rocks.zipcode.service.dto.GolferDTO;
import rocks.zipcode.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Golfer} and its DTO {@link GolferDTO}.
 */
@Mapper(componentModel = "spring")
public interface GolferMapper extends EntityMapper<GolferDTO, Golfer> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    GolferDTO toDto(Golfer s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
