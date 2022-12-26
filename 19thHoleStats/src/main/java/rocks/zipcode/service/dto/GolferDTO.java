package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rocks.zipcode.domain.Golfer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GolferDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Double avgScore;

    private Double roundsPlayed;

    private Double handicap;

    private UserDTO user;

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

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Double getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(Double roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public Double getHandicap() {
        return handicap;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GolferDTO)) {
            return false;
        }

        GolferDTO golferDTO = (GolferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, golferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GolferDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", avgScore=" + getAvgScore() +
            ", roundsPlayed=" + getRoundsPlayed() +
            ", handicap=" + getHandicap() +
            ", user=" + getUser() +
            "}";
    }
}
