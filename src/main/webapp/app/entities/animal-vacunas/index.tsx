import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnimalVacunas from './animal-vacunas';
import AnimalVacunasDetail from './animal-vacunas-detail';
import AnimalVacunasUpdate from './animal-vacunas-update';
import AnimalVacunasDeleteDialog from './animal-vacunas-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AnimalVacunasDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnimalVacunasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnimalVacunasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnimalVacunasDetail} />
      <ErrorBoundaryRoute path={match.url} component={AnimalVacunas} />
    </Switch>
  </>
);

export default Routes;
