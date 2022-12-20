import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Golfer from './golfer';
import Club from './club';
import Course from './course';
import Scorecard from './scorecard';
import Round from './round';
import Hole from './hole';
import HoleData from './hole-data';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="golfer/*" element={<Golfer />} />
        <Route path="club/*" element={<Club />} />
        <Route path="course/*" element={<Course />} />
        <Route path="scorecard/*" element={<Scorecard />} />
        <Route path="round/*" element={<Round />} />
        <Route path="hole/*" element={<Hole />} />
        <Route path="hole-data/*" element={<HoleData />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
