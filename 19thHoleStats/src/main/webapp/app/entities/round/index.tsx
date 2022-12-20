import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Round from './round';
import RoundDetail from './round-detail';
import RoundUpdate from './round-update';
import RoundDeleteDialog from './round-delete-dialog';

const RoundRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Round />} />
    <Route path="new" element={<RoundUpdate />} />
    <Route path=":id">
      <Route index element={<RoundDetail />} />
      <Route path="edit" element={<RoundUpdate />} />
      <Route path="delete" element={<RoundDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RoundRoutes;
