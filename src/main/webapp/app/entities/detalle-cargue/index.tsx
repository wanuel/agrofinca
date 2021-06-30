import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DetalleCargue from './detalle-cargue';
import DetalleCargueDetail from './detalle-cargue-detail';
import DetalleCargueUpdate from './detalle-cargue-update';
import DetalleCargueDeleteDialog from './detalle-cargue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DetalleCargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DetalleCargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DetalleCargueDetail} />
      <ErrorBoundaryRoute path={match.url} component={DetalleCargue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DetalleCargueDeleteDialog} />
  </>
);

export default Routes;
