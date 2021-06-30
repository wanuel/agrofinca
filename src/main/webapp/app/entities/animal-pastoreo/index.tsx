import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnimalPastoreo from './animal-pastoreo';
import AnimalPastoreoDetail from './animal-pastoreo-detail';
import AnimalPastoreoUpdate from './animal-pastoreo-update';
import AnimalPastoreoDeleteDialog from './animal-pastoreo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnimalPastoreoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnimalPastoreoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnimalPastoreoDetail} />
      <ErrorBoundaryRoute path={match.url} component={AnimalPastoreo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AnimalPastoreoDeleteDialog} />
  </>
);

export default Routes;
