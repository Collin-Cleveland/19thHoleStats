package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.Hole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoleDTO implements Serializable {

    private Long id;

    @NotNull
    @Max(value = 18)
    private Integer holeNumber;

    @NotNull
    private Integer par;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(Integer holeNumber) {
        this.holeNumber = holeNumber;
    }

    public Integer getPar() {
        return par;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoleDTO)) {
            return false;
        }

        HoleDTO holeDTO = (HoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, holeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoleDTO{" +
            "id=" + getId() +
            ", holeNumber=" + getHoleNumber() +
            ", par=" + getPar() +
            ", course=" + getCourse() +
            "}";
    }
}
