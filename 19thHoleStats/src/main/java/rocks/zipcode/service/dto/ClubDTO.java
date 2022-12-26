package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.Club} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClubDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String state;

    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClubDTO)) {
            return false;
        }

        ClubDTO clubDTO = (ClubDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clubDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClubDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
