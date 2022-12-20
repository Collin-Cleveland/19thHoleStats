package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HoleData.
 */
@Entity
@Table(name = "hole_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoleData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hole_score")
    private Integer holeScore;

    @Column(name = "putts")
    private Integer putts;

    @Column(name = "fairway_hit")
    private Boolean fairwayHit;

    @JsonIgnoreProperties(value = { "course", "holeData" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Hole hole;

    @ManyToOne
    @JsonIgnoreProperties(value = { "course", "round", "holeData" }, allowSetters = true)
    private Scorecard scorecard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HoleData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoleScore() {
        return this.holeScore;
    }

    public HoleData holeScore(Integer holeScore) {
        this.setHoleScore(holeScore);
        return this;
    }

    public void setHoleScore(Integer holeScore) {
        this.holeScore = holeScore;
    }

    public Integer getPutts() {
        return this.putts;
    }

    public HoleData putts(Integer putts) {
        this.setPutts(putts);
        return this;
    }

    public void setPutts(Integer putts) {
        this.putts = putts;
    }

    public Boolean getFairwayHit() {
        return this.fairwayHit;
    }

    public HoleData fairwayHit(Boolean fairwayHit) {
        this.setFairwayHit(fairwayHit);
        return this;
    }

    public void setFairwayHit(Boolean fairwayHit) {
        this.fairwayHit = fairwayHit;
    }

    public Hole getHole() {
        return this.hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public HoleData hole(Hole hole) {
        this.setHole(hole);
        return this;
    }

    public Scorecard getScorecard() {
        return this.scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

    public HoleData scorecard(Scorecard scorecard) {
        this.setScorecard(scorecard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoleData)) {
            return false;
        }
        return id != null && id.equals(((HoleData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoleData{" +
            "id=" + getId() +
            ", holeScore=" + getHoleScore() +
            ", putts=" + getPutts() +
            ", fairwayHit='" + getFairwayHit() + "'" +
            "}";
    }
}
