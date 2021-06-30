import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PotreroActividadAnimal from './potrero-actividad-animal';
import PotreroActividadAnimalDetail from './potrero-actividad-animal-detail';
import PotreroActividadAnimalUpdate from './potrero-actividad-animal-update';
import PotreroActividadAnimalDeleteDialog from './potrero-actividad-animal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PotreroActividadAnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PotreroActividadAnimalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PotreroActividadAnimalDetail} />
      <ErrorBoundaryRoute path={match.url} component={PotreroActividadAnimal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PotreroActividadAnimalDeleteDialog} />
  </>
);

export default Routes;
