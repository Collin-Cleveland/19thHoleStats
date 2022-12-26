package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Golfer.
 */
@Entity
@Table(name = "golfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Golfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avg_score")
    private Double avgScore;

    @Column(name = "rounds_played")
    private Double roundsPlayed;

    @Column(name = "handicap")
    private Double handicap;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "golfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "scorecard", "course", "golfer" }, allowSetters = true)
    private Set<Round> rounds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Golfer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Golfer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvgScore() {
        return this.avgScore;
    }

    public Golfer avgScore(Double avgScore) {
        this.setAvgScore(avgScore);
        return this;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Double getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public Golfer roundsPlayed(Double roundsPlayed) {
        this.setRoundsPlayed(roundsPlayed);
        return this;
    }

    public void setRoundsPlayed(Double roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public Double getHandicap() {
        return this.handicap;
    }

    public Golfer handicap(Double handicap) {
        this.setHandicap(handicap);
        return this;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Golfer user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Round> getRounds() {
        return this.rounds;
    }

    public void setRounds(Set<Round> rounds) {
        if (this.rounds != null) {
            this.rounds.forEach(i -> i.setGolfer(null));
        }
        if (rounds != null) {
            rounds.forEach(i -> i.setGolfer(this));
        }
        this.rounds = rounds;
    }

    public Golfer rounds(Set<Round> rounds) {
        this.setRounds(rounds);
        return this;
    }

    public Golfer addRound(Round round) {
        this.rounds.add(round);
        round.setGolfer(this);
        return this;
    }

    public Golfer removeRound(Round round) {
        this.rounds.remove(round);
        round.setGolfer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Golfer)) {
            return false;
        }
        return id != null && id.equals(((Golfer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Golfer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", avgScore=" + getAvgScore() +
            ", roundsPlayed=" + getRoundsPlayed() +
            ", handicap=" + getHandicap() +
            "}";
    }
}
