package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import rocks.zipcode.domain.enumeration.TeeColor;

/**
 * A DTO for the {@link rocks.zipcode.domain.Scorecard} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScorecardDTO implements Serializable {

    private Long id;

    private TeeColor teeColor;

    private Integer totalScore;

    private Integer totalPutts;

    private Integer fairwaysHit;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeeColor getTeeColor() {
        return teeColor;
    }

    public void setTeeColor(TeeColor teeColor) {
        this.teeColor = teeColor;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalPutts() {
        return totalPutts;
    }

    public void setTotalPutts(Integer totalPutts) {
        this.totalPutts = totalPutts;
    }

    public Integer getFairwaysHit() {
        return fairwaysHit;
    }

    public void setFairwaysHit(Integer fairwaysHit) {
        this.fairwaysHit = fairwaysHit;
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
        if (!(o instanceof ScorecardDTO)) {
            return false;
        }

        ScorecardDTO scorecardDTO = (ScorecardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scorecardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScorecardDTO{" +
            "id=" + getId() +
            ", teeColor='" + getTeeColor() + "'" +
            ", totalScore=" + getTotalScore() +
            ", totalPutts=" + getTotalPutts() +
            ", fairwaysHit=" + getFairwaysHit() +
            ", course=" + getCourse() +
            "}";
    }
}
