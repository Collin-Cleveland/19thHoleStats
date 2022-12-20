import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HoleData from './hole-data';
import HoleDataDetail from './hole-data-detail';
import HoleDataUpdate from './hole-data-update';
import HoleDataDeleteDialog from './hole-data-delete-dialog';

const HoleDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HoleData />} />
    <Route path="new" element={<HoleDataUpdate />} />
    <Route path=":id">
      <Route index element={<HoleDataDetail />} />
      <Route path="edit" element={<HoleDataUpdate />} />
      <Route path="delete" element={<HoleDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoleDataRoutes;
