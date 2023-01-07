package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Comment;
import rocks.zipcode.domain.Video;
import rocks.zipcode.domain.VideoUser;
import rocks.zipcode.service.dto.CommentDTO;
import rocks.zipcode.service.dto.VideoDTO;
import rocks.zipcode.service.dto.VideoUserDTO;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "video", source = "video", qualifiedByName = "videoId")
    @Mapping(target = "videoUser", source = "videoUser", qualifiedByName = "videoUserId")
    CommentDTO toDto(Comment s);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);

    @Named("videoUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoUserDTO toDtoVideoUserId(VideoUser videoUser);
}
