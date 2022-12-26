package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.HoleData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoleDataDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer holeScore;

    private Integer putts;

    private Boolean fairwayHit;

    private HoleDTO hole;

    private ScorecardDTO scorecard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoleScore() {
        return holeScore;
    }

    public void setHoleScore(Integer holeScore) {
        this.holeScore = holeScore;
    }

    public Integer getPutts() {
        return putts;
    }

    public void setPutts(Integer putts) {
        this.putts = putts;
    }

    public Boolean getFairwayHit() {
        return fairwayHit;
    }

    public void setFairwayHit(Boolean fairwayHit) {
        this.fairwayHit = fairwayHit;
    }

    public HoleDTO getHole() {
        return hole;
    }

    public void setHole(HoleDTO hole) {
        this.hole = hole;
    }

    public ScorecardDTO getScorecard() {
        return scorecard;
    }

    public void setScorecard(ScorecardDTO scorecard) {
        this.scorecard = scorecard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoleDataDTO)) {
            return false;
        }

        HoleDataDTO holeDataDTO = (HoleDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, holeDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoleDataDTO{" +
            "id=" + getId() +
            ", holeScore=" + getHoleScore() +
            ", putts=" + getPutts() +
            ", fairwayHit='" + getFairwayHit() + "'" +
            ", hole=" + getHole() +
            ", scorecard=" + getScorecard() +
            "}";
    }
}
