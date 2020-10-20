import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lote from './lote';
import AnimalLote from './animal-lote';
/* jhipster-needle-add-route-import - JHipster will add routes here */
import Finca from './finca';
import Potrero from './potrero';
import Persona from './persona';
import PotreroActividad from './potrero-actividad';
import Animal from './animal';
import AnimalFicha from './animal-ficha';
import Parametros from './parametros';
import AnimalCostos from './animal-costos';
import AnimalEvento from './animal-evento';
import AnimalPeso from './animal-peso';
import AnimalImagen from './animal-imagen';
import AnimalVacunas from './animal-vacunas';
import PotreroActividadAnimal from './potrero-actividad-animal';

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}lote`} component={Lote} />
      <ErrorBoundaryRoute path={`${match.url}animal-lote`} component={AnimalLote} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      <ErrorBoundaryRoute path={`${match.url}finca`} component={Finca} />
      <ErrorBoundaryRoute path={`${match.url}potrero`} component={Potrero} />
      <ErrorBoundaryRoute path={`${match.url}persona`} component={Persona} />
      <ErrorBoundaryRoute path={`${match.url}potrero-actividad`} component={PotreroActividad} />
      <ErrorBoundaryRoute path={`${match.url}animal`} component={Animal} />
      <ErrorBoundaryRoute path={`${match.url}parametros`} component={Parametros} />
      <ErrorBoundaryRoute path={`${match.url}animal-costos`} component={AnimalCostos} />
      <ErrorBoundaryRoute path={`${match.url}animal-evento`} component={AnimalEvento} />
      <ErrorBoundaryRoute path={`${match.url}animal-peso`} component={AnimalPeso} />
      <ErrorBoundaryRoute path={`${match.url}animal-imagen`} component={AnimalImagen} />
      <ErrorBoundaryRoute path={`${match.url}animal-vacunas`} component={AnimalVacunas} />
      <ErrorBoundaryRoute path={`${match.url}potrero-actividad-animal`} component={PotreroActividadAnimal} />
      <ErrorBoundaryRoute path={`${match.url}animal-ficha`} component={AnimalFicha} />
    </Switch>
  </div>
);

export default Routes;
