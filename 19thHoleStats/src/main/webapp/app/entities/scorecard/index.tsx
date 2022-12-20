import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Scorecard from './scorecard';
import ScorecardDetail from './scorecard-detail';
import ScorecardUpdate from './scorecard-update';
import ScorecardDeleteDialog from './scorecard-delete-dialog';

const ScorecardRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Scorecard />} />
    <Route path="new" element={<ScorecardUpdate />} />
    <Route path=":id">
      <Route index element={<ScorecardDetail />} />
      <Route path="edit" element={<ScorecardUpdate />} />
      <Route path="delete" element={<ScorecardDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScorecardRoutes;
