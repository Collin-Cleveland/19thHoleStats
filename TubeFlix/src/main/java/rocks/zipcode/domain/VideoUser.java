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
 * A VideoUser.
 */
@Entity
@Table(name = "video_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VideoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "uploader")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "uploader", "comments" }, allowSetters = true)
    private Set<Video> videos = new HashSet<>();

    @OneToMany(mappedBy = "video")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videoUser", "video" }, allowSetters = true)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "videoUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "video", "videoUser" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VideoUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public VideoUser userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public VideoUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setUploader(null));
        }
        if (videos != null) {
            videos.forEach(i -> i.setUploader(this));
        }
        this.videos = videos;
    }

    public VideoUser videos(Set<Video> videos) {
        this.setVideos(videos);
        return this;
    }

    public VideoUser addVideo(Video video) {
        this.videos.add(video);
        video.setUploader(this);
        return this;
    }

    public VideoUser removeVideo(Video video) {
        this.videos.remove(video);
        video.setUploader(null);
        return this;
    }

    public Set<Like> getLikes() {
        return this.likes;
    }

    public void setLikes(Set<Like> likes) {
        if (this.likes != null) {
            this.likes.forEach(i -> i.setVideo(null));
        }
        if (likes != null) {
            likes.forEach(i -> i.setVideo(this));
        }
        this.likes = likes;
    }

    public VideoUser likes(Set<Like> likes) {
        this.setLikes(likes);
        return this;
    }

    public VideoUser addLike(Like like) {
        this.likes.add(like);
        like.setVideo(this);
        return this;
    }

    public VideoUser removeLike(Like like) {
        this.likes.remove(like);
        like.setVideo(null);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setVideoUser(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setVideoUser(this));
        }
        this.comments = comments;
    }

    public VideoUser comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public VideoUser addComment(Comment comment) {
        this.comments.add(comment);
        comment.setVideoUser(this);
        return this;
    }

    public VideoUser removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setVideoUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VideoUser)) {
            return false;
        }
        return id != null && id.equals(((VideoUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VideoUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
