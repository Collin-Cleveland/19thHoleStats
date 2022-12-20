import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hole from './hole';
import HoleDetail from './hole-detail';
import HoleUpdate from './hole-update';
import HoleDeleteDialog from './hole-delete-dialog';

const HoleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hole />} />
    <Route path="new" element={<HoleUpdate />} />
    <Route path=":id">
      <Route index element={<HoleDetail />} />
      <Route path="edit" element={<HoleUpdate />} />
      <Route path="delete" element={<HoleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoleRoutes;
