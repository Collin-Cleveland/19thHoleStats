package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.VideoUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String userName;

    private UserDTO internalUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoUserDTO)) {
            return false;
        }

        VideoUserDTO videoUserDTO = (VideoUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, videoUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoUserDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
