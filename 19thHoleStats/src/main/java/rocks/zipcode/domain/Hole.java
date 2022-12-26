package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hole.
 */
@Entity
@Table(name = "hole")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Max(value = 18)
    @Column(name = "hole_number", nullable = false)
    private Integer holeNumber;

    @NotNull
    @Column(name = "par", nullable = false)
    private Integer par;

    @ManyToOne
    @JsonIgnoreProperties(value = { "club", "scorecard", "holes", "rounds" }, allowSetters = true)
    private Course course;

    @JsonIgnoreProperties(value = { "hole", "scorecard" }, allowSetters = true)
    @OneToOne(mappedBy = "hole")
    private HoleData holeData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHoleNumber() {
        return this.holeNumber;
    }

    public Hole holeNumber(Integer holeNumber) {
        this.setHoleNumber(holeNumber);
        return this;
    }

    public void setHoleNumber(Integer holeNumber) {
        this.holeNumber = holeNumber;
    }

    public Integer getPar() {
        return this.par;
    }

    public Hole par(Integer par) {
        this.setPar(par);
        return this;
    }

    public void setPar(Integer par) {
        this.par = par;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Hole course(Course course) {
        this.setCourse(course);
        return this;
    }

    public HoleData getHoleData() {
        return this.holeData;
    }

    public void setHoleData(HoleData holeData) {
        if (this.holeData != null) {
            this.holeData.setHole(null);
        }
        if (holeData != null) {
            holeData.setHole(this);
        }
        this.holeData = holeData;
    }

    public Hole holeData(HoleData holeData) {
        this.setHoleData(holeData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hole)) {
            return false;
        }
        return id != null && id.equals(((Hole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hole{" +
            "id=" + getId() +
            ", holeNumber=" + getHoleNumber() +
            ", par=" + getPar() +
            "}";
    }
}
