import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoParametro from './tipo-parametro';
import TipoParametroDetail from './tipo-parametro-detail';
import TipoParametroUpdate from './tipo-parametro-update';
import TipoParametroDeleteDialog from './tipo-parametro-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TipoParametroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TipoParametroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TipoParametroDetail} />
      <ErrorBoundaryRoute path={match.url} component={TipoParametro} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TipoParametroDeleteDialog} />
  </>
);

export default Routes;
