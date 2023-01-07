package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Video;
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.service.dto.VideoDTO;
import rocks.zipcode.service.dto.VideoUserDTO;

/**
 * Mapper for the entity {@link Video} and its DTO {@link VideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {
    @Mapping(target = "uploader", source = "uploader", qualifiedByName = "videoUserId")
    VideoDTO toDto(Video s);

    @Named("videoUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoUserDTO toDtoVideoUserId(VideoUser videoUser);
}
