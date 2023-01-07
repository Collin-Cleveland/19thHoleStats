package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.Video} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoDTO implements Serializable {

    private Long id;

    @NotNull
    private String videoLink;

    @NotNull
    private String title;

    private String description;

    private LocalDate uploadDate;

    private VideoUserDTO uploader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public VideoUserDTO getUploader() {
        return uploader;
    }

    public void setUploader(VideoUserDTO uploader) {
        this.uploader = uploader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoDTO)) {
            return false;
        }

        VideoDTO videoDTO = (VideoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, videoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoDTO{" +
            "id=" + getId() +
            ", videoLink='" + getVideoLink() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", uploader=" + getUploader() +
            "}";
    }
}
