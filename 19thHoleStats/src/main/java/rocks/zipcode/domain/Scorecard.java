package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import rocks.zipcode.domain.enumeration.TeeColor;

/**
 * A Scorecard.
 */
@Entity
@Table(name = "scorecard")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Scorecard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tee_color")
    private TeeColor teeColor;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "total_putts")
    private Integer totalPutts;

    @Column(name = "fairways_hit")
    private Integer fairwaysHit;

    @JsonIgnoreProperties(value = { "club", "scorecard", "holes", "rounds" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Course course;

    @JsonIgnoreProperties(value = { "scorecard", "course", "golfer" }, allowSetters = true)
    @OneToOne(mappedBy = "scorecard")
    private Round round;

    @OneToMany(mappedBy = "scorecard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hole", "scorecard" }, allowSetters = true)
    private Set<HoleData> holeData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Scorecard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeeColor getTeeColor() {
        return this.teeColor;
    }

    public Scorecard teeColor(TeeColor teeColor) {
        this.setTeeColor(teeColor);
        return this;
    }

    public void setTeeColor(TeeColor teeColor) {
        this.teeColor = teeColor;
    }

    public Integer getTotalScore() {
        return this.totalScore;
    }

    public Scorecard totalScore(Integer totalScore) {
        this.setTotalScore(totalScore);
        return this;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalPutts() {
        return this.totalPutts;
    }

    public Scorecard totalPutts(Integer totalPutts) {
        this.setTotalPutts(totalPutts);
        return this;
    }

    public void setTotalPutts(Integer totalPutts) {
        this.totalPutts = totalPutts;
    }

    public Integer getFairwaysHit() {
        return this.fairwaysHit;
    }

    public Scorecard fairwaysHit(Integer fairwaysHit) {
        this.setFairwaysHit(fairwaysHit);
        return this;
    }

    public void setFairwaysHit(Integer fairwaysHit) {
        this.fairwaysHit = fairwaysHit;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Scorecard course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Round getRound() {
        return this.round;
    }

    public void setRound(Round round) {
        if (this.round != null) {
            this.round.setScorecard(null);
        }
        if (round != null) {
            round.setScorecard(this);
        }
        this.round = round;
    }

    public Scorecard round(Round round) {
        this.setRound(round);
        return this;
    }

    public Set<HoleData> getHoleData() {
        return this.holeData;
    }

    public void setHoleData(Set<HoleData> holeData) {
        if (this.holeData != null) {
            this.holeData.forEach(i -> i.setScorecard(null));
        }
        if (holeData != null) {
            holeData.forEach(i -> i.setScorecard(this));
        }
        this.holeData = holeData;
    }

    public Scorecard holeData(Set<HoleData> holeData) {
        this.setHoleData(holeData);
        return this;
    }

    public Scorecard addHoleData(HoleData holeData) {
        this.holeData.add(holeData);
        holeData.setScorecard(this);
        return this;
    }

    public Scorecard removeHoleData(HoleData holeData) {
        this.holeData.remove(holeData);
        holeData.setScorecard(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scorecard)) {
            return false;
        }
        return id != null && id.equals(((Scorecard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scorecard{" +
            "id=" + getId() +
            ", teeColor='" + getTeeColor() + "'" +
            ", totalScore=" + getTotalScore() +
            ", totalPutts=" + getTotalPutts() +
            ", fairwaysHit=" + getFairwaysHit() +
            "}";
    }
}
