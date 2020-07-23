import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PotreroActividad from './potrero-actividad';
import PotreroActividadDetail from './potrero-actividad-detail';
import PotreroActividadUpdate from './potrero-actividad-update';
import PotreroActividadDeleteDialog from './potrero-actividad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PotreroActividadDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PotreroActividadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PotreroActividadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PotreroActividadDetail} />
      <ErrorBoundaryRoute path={match.url} component={PotreroActividad} />
    </Switch>
  </>
);

export default Routes;
