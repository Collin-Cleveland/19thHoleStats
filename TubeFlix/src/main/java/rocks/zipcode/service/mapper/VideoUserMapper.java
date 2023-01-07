package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.User;
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.service.dto.UserDTO;
import rocks.zipcode.service.dto.VideoUserDTO;

/**
 * Mapper for the entity {@link VideoUser} and its DTO {@link VideoUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface VideoUserMapper extends EntityMapper<VideoUserDTO, VideoUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    VideoUserDTO toDto(VideoUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
