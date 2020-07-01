import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

/* jhipster-needle-add-route-import - JHipster will add routes here */
import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';
import Finca from './finca/finca';
import FincaDetail from './finca/finca-detail';
import FincaUpdate from './finca/finca-update';
import FincaDeleteDialog from './finca/finca-delete-dialog';

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
	  <PrivateRoute path="/finca" component={Finca} hasAnyAuthorities={[AUTHORITIES.USER]} />
	  <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FincaDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FincaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FincaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FincaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Finca} />
    </Switch>
  </div>
);

export default Routes;
