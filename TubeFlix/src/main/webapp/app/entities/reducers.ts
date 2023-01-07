import videoUser from 'app/entities/video-user/video-user.reducer';
import video from 'app/entities/video/video.reducer';
import comment from 'app/entities/comment/comment.reducer';
import like from 'app/entities/like/like.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  videoUser,
  video,
  comment,
  like,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
