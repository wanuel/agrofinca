import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ErrorCargue from './error-cargue';
import ErrorCargueDetail from './error-cargue-detail';
import ErrorCargueUpdate from './error-cargue-update';
import ErrorCargueDeleteDialog from './error-cargue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ErrorCargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ErrorCargueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ErrorCargueDetail} />
      <ErrorBoundaryRoute path={match.url} component={ErrorCargue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ErrorCargueDeleteDialog} />
  </>
);

export default Routes;
