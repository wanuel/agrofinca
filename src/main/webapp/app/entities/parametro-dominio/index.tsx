import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParametroDominio from './parametro-dominio';
import ParametroDominioDetail from './parametro-dominio-detail';
import ParametroDominioUpdate from './parametro-dominio-update';
import ParametroDominioDeleteDialog from './parametro-dominio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParametroDominioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParametroDominioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParametroDominioDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParametroDominio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParametroDominioDeleteDialog} />
  </>
);

export default Routes;
