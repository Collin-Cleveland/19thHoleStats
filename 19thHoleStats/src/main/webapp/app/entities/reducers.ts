import golfer from 'app/entities/golfer/golfer.reducer';
import club from 'app/entities/club/club.reducer';
import course from 'app/entities/course/course.reducer';
import scorecard from 'app/entities/scorecard/scorecard.reducer';
import round from 'app/entities/round/round.reducer';
import hole from 'app/entities/hole/hole.reducer';
import holeData from 'app/entities/hole-data/hole-data.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  golfer,
  club,
  course,
  scorecard,
  round,
  hole,
  holeData,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
