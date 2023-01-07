package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Like;
import rocks.zipcode.domain.Video;
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.service.dto.LikeDTO;
import rocks.zipcode.service.dto.VideoDTO;
import rocks.zipcode.service.dto.VideoUserDTO;

/**
 * Mapper for the entity {@link Like} and its DTO {@link LikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LikeMapper extends EntityMapper<LikeDTO, Like> {
    @Mapping(target = "videoUser", source = "videoUser", qualifiedByName = "videoId")
    @Mapping(target = "video", source = "video", qualifiedByName = "videoUserId")
    LikeDTO toDto(Like s);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);

    @Named("videoUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoUserDTO toDtoVideoUserId(VideoUser videoUser);
}
