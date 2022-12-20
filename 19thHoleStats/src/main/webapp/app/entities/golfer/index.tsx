import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Golfer from './golfer';
import GolferDetail from './golfer-detail';
import GolferUpdate from './golfer-update';
import GolferDeleteDialog from './golfer-delete-dialog';

const GolferRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Golfer />} />
    <Route path="new" element={<GolferUpdate />} />
    <Route path=":id">
      <Route index element={<GolferDetail />} />
      <Route path="edit" element={<GolferUpdate />} />
      <Route path="delete" element={<GolferDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GolferRoutes;
