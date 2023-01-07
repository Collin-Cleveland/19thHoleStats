package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment_date")
    private LocalDate commentDate;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JsonIgnoreProperties(value = { "uploader", "comments" }, allowSetters = true)
    private Video video;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "videos", "likes", "comments" }, allowSetters = true)
    private VideoUser videoUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCommentDate() {
        return this.commentDate;
    }

    public Comment commentDate(LocalDate commentDate) {
        this.setCommentDate(commentDate);
        return this;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }

    public String getBody() {
        return this.body;
    }

    public Comment body(String body) {
        this.setBody(body);
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Video getVideo() {
        return this.video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Comment video(Video video) {
        this.setVideo(video);
        return this;
    }

    public VideoUser getVideoUser() {
        return this.videoUser;
    }

    public void setVideoUser(VideoUser videoUser) {
        this.videoUser = videoUser;
    }

    public Comment videoUser(VideoUser videoUser) {
        this.setVideoUser(videoUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", commentDate='" + getCommentDate() + "'" +
            ", body='" + getBody() + "'" +
            "}";
    }
}
