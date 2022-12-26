package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Round} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoundDTO implements Serializable {

    private Long id;

    private ZonedDateTime datePlayed;

    private Integer numOfHolesPlayed;

    private ScorecardDTO scorecard;

    private CourseDTO course;

    private GolferDTO golfer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(ZonedDateTime datePlayed) {
        this.datePlayed = datePlayed;
    }

    public Integer getNumOfHolesPlayed() {
        return numOfHolesPlayed;
    }

    public void setNumOfHolesPlayed(Integer numOfHolesPlayed) {
        this.numOfHolesPlayed = numOfHolesPlayed;
    }

    public ScorecardDTO getScorecard() {
        return scorecard;
    }

    public void setScorecard(ScorecardDTO scorecard) {
        this.scorecard = scorecard;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public GolferDTO getGolfer() {
        return golfer;
    }

    public void setGolfer(GolferDTO golfer) {
        this.golfer = golfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoundDTO)) {
            return false;
        }

        RoundDTO roundDTO = (RoundDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roundDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoundDTO{" +
            "id=" + getId() +
            ", datePlayed='" + getDatePlayed() + "'" +
            ", numOfHolesPlayed=" + getNumOfHolesPlayed() +
            ", scorecard=" + getScorecard() +
            ", course=" + getCourse() +
            ", golfer=" + getGolfer() +
            "}";
    }
}
