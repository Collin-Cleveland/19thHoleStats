package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "par")
    private Integer par;

    @ManyToOne
    @JsonIgnoreProperties(value = { "courses" }, allowSetters = true)
    private Club club;

    @JsonIgnoreProperties(value = { "course", "round", "holeData" }, allowSetters = true)
    @OneToOne(mappedBy = "course")
    private Scorecard scorecard;

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "course", "holeData" }, allowSetters = true)
    private Set<Hole> holes = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "scorecard", "course", "golfer" }, allowSetters = true)
    private Set<Round> rounds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Course name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPar() {
        return this.par;
    }

    public Course par(Integer par) {
        this.setPar(par);
        return this;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Course club(Club club) {
        this.setClub(club);
        return this;
    }

    public Scorecard getScorecard() {
        return this.scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        if (this.scorecard != null) {
            this.scorecard.setCourse(null);
        }
        if (scorecard != null) {
            scorecard.setCourse(this);
        }
        this.scorecard = scorecard;
    }

    public Course scorecard(Scorecard scorecard) {
        this.setScorecard(scorecard);
        return this;
    }

    public Set<Hole> getHoles() {
        return this.holes;
    }

    public void setHoles(Set<Hole> holes) {
        if (this.holes != null) {
            this.holes.forEach(i -> i.setCourse(null));
        }
        if (holes != null) {
            holes.forEach(i -> i.setCourse(this));
        }
        this.holes = holes;
    }

    public Course holes(Set<Hole> holes) {
        this.setHoles(holes);
        return this;
    }

    public Course addHole(Hole hole) {
        this.holes.add(hole);
        hole.setCourse(this);
        return this;
    }

    public Course removeHole(Hole hole) {
        this.holes.remove(hole);
        hole.setCourse(null);
        return this;
    }

    public Set<Round> getRounds() {
        return this.rounds;
    }

    public void setRounds(Set<Round> rounds) {
        if (this.rounds != null) {
            this.rounds.forEach(i -> i.setCourse(null));
        }
        if (rounds != null) {
            rounds.forEach(i -> i.setCourse(this));
        }
        this.rounds = rounds;
    }

    public Course rounds(Set<Round> rounds) {
        this.setRounds(rounds);
        return this;
    }

    public Course addRound(Round round) {
        this.rounds.add(round);
        round.setCourse(this);
        return this;
    }

    public Course removeRound(Round round) {
        this.rounds.remove(round);
        round.setCourse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", par=" + getPar() +
            "}";
    }
}
