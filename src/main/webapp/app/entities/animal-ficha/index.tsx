import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnimalFicha from './animal-ficha';
import AnimalDetail from './animal-ficha-detail';
import AnimalUpdate from './animal-ficha-update';
import AnimalDeleteDialog from './animal-ficha-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AnimalDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnimalDetail} />
      <ErrorBoundaryRoute path={match.url} component={AnimalFicha} />
    </Switch>
  </>
);

export default Routes;
