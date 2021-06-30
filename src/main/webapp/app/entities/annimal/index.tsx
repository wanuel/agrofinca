import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Annimal from './annimal';
import AnnimalDetail from './annimal-detail';
import AnnimalUpdate from './annimal-update';
import AnnimalDeleteDialog from './annimal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnnimalDetail} />
      <ErrorBoundaryRoute path={match.url} component={Annimal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AnnimalDeleteDialog} />
  </>
);

export default Routes;
