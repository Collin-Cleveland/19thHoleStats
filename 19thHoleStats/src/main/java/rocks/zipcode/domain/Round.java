package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Round.
 */
@Entity
@Table(name = "round")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Round implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_played")
    private ZonedDateTime datePlayed;

    @Column(name = "num_of_holes_played")
    private Integer numOfHolesPlayed;

    @JsonIgnoreProperties(value = { "course", "round", "holeData" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Scorecard scorecard;

    @ManyToOne
    @JsonIgnoreProperties(value = { "club", "scorecard", "holes", "rounds" }, allowSetters = true)
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "rounds" }, allowSetters = true)
    private Golfer golfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Round id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDatePlayed() {
        return this.datePlayed;
    }

    public Round datePlayed(ZonedDateTime datePlayed) {
        this.setDatePlayed(datePlayed);
        return this;
    }

    public void setDatePlayed(ZonedDateTime datePlayed) {
        this.datePlayed = datePlayed;
    }

    public Integer getNumOfHolesPlayed() {
        return this.numOfHolesPlayed;
    }

    public Round numOfHolesPlayed(Integer numOfHolesPlayed) {
        this.setNumOfHolesPlayed(numOfHolesPlayed);
        return this;
    }

    public void setNumOfHolesPlayed(Integer numOfHolesPlayed) {
        this.numOfHolesPlayed = numOfHolesPlayed;
    }

    public Scorecard getScorecard() {
        return this.scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

    public Round scorecard(Scorecard scorecard) {
        this.setScorecard(scorecard);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Round course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Golfer getGolfer() {
        return this.golfer;
    }

    public void setGolfer(Golfer golfer) {
        this.golfer = golfer;
    }

    public Round golfer(Golfer golfer) {
        this.setGolfer(golfer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Round)) {
            return false;
        }
        return id != null && id.equals(((Round) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Round{" +
            "id=" + getId() +
            ", datePlayed='" + getDatePlayed() + "'" +
            ", numOfHolesPlayed=" + getNumOfHolesPlayed() +
            "}";
    }
}
