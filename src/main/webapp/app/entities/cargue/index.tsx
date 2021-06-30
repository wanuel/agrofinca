import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cargue from './cargue';
import CargueDetail from './cargue-detail';
import CargueUpdate from './cargue-update';
import CargueDeleteDialog from './cargue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CargueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cargue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CargueDeleteDialog} />
  </>
);

export default Routes;
