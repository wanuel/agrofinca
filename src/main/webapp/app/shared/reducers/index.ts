import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import lote, {
  LoteState
} from 'app/entities/lote/lote.reducer';
// prettier-ignore
import animalLote, {
  AnimalLoteState
} from 'app/entities/animal-lote/animal-lote.reducer';
// prettier-ignore
// prettier-ignore
import cargue from 'app/entities/cargue/cargue.reducer';
// prettier-ignore
import detalleCargue from 'app/entities/detalle-cargue/detalle-cargue.reducer';
// prettier-ignore
import errorCargue from 'app/entities/error-cargue/error-cargue.reducer';
// prettier-ignore
import tipoParametro from 'app/entities/tipo-parametro/tipo-parametro.reducer';
// prettier-ignore
import parametroDominio from 'app/entities/parametro-dominio/parametro-dominio.reducer';
// prettier-ignore
import potrero from 'app/entities/potrero/potrero.reducer';
// prettier-ignore
import finca from 'app/entities/finca/finca.reducer';
// prettier-ignore
import potreroActividad from 'app/entities/potrero-actividad/potrero-actividad.reducer';
// prettier-ignore
import animal from 'app/entities/animal/animal.reducer';
// prettier-ignore
import animalPastoreo from 'app/entities/animal-pastoreo/animal-pastoreo.reducer';
// prettier-ignore
import parametros from 'app/entities/parametros/parametros.reducer';
// prettier-ignore
import animalCostos from 'app/entities/animal-costos/animal-costos.reducer';
// prettier-ignore
import animalEvento from 'app/entities/animal-evento/animal-evento.reducer';
// prettier-ignore
import animalPeso from 'app/entities/animal-peso/animal-peso.reducer';
// prettier-ignore
import animalImagen from 'app/entities/animal-imagen/animal-imagen.reducer';
// prettier-ignore
import animalVacunas from 'app/entities/animal-vacunas/animal-vacunas.reducer';
// prettier-ignore
import persona from 'app/entities/persona/persona.reducer';
// prettier-ignore
import potreroActividadAnimal from 'app/entities/potrero-actividad-animal/potrero-actividad-animal.reducer';
// prettier-ignore
import lote from 'app/entities/lote/lote.reducer';
// prettier-ignore
import animalLote from 'app/entities/animal-lote/animal-lote.reducer';
// prettier-ignore
import annimal from 'app/entities/annimal/annimal.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */
import potrero, { PotreroState } from 'app/entities/potrero/potrero.reducer';
// prettier-ignore
import finca, { FincaState } from 'app/entities/finca/finca.reducer';
// prettier-ignore
import persona, { PersonaState } from 'app/entities/persona/persona.reducer';
import potreroActividad, { PotreroActividadState } from 'app/entities/potrero-actividad/potrero-actividad.reducer';
import animal, { animalestate } from 'app/entities/animal/animal.reducer';
import parametros, { ParametrosState } from 'app/entities/parametros/parametros.reducer';
import animalCostos, { AnimalCostosState } from 'app/entities/animal-costos/animal-costos.reducer';
import animalEvento, { AnimalEventoState } from 'app/entities/animal-evento/animal-evento.reducer';
import animalPeso, { AnimalPesoState } from 'app/entities/animal-peso/animal-peso.reducer';
import animalImagen, { AnimalImagenState } from 'app/entities/animal-imagen/animal-imagen.reducer';
import animalVacunas, { AnimalVacunasState } from 'app/entities/animal-vacunas/animal-vacunas.reducer';
import potreroActividadAnimal, {
  PotreroActividadanimalestate,
} from 'app/entities/potrero-actividad-animal/potrero-actividad-animal.reducer';
import animalFicha, { AnimalFichaState } from 'app/entities/animal-ficha/animal-ficha.reducer';

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly lote: LoteState;
  readonly animalLote: AnimalLoteState;

  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly finca: FincaState;
  readonly potrero: PotreroState;
  readonly persona: PersonaState;
  readonly potreroActividad: PotreroActividadState;
  readonly animal: animalestate;
  readonly parametros: ParametrosState;
  readonly animalCostos: AnimalCostosState;
  readonly animalEvento: AnimalEventoState;
  readonly animalPeso: AnimalPesoState;
  readonly animalImagen: AnimalImagenState;
  readonly animalVacunas: AnimalVacunasState;
  readonly potreroActividadAnimal: PotreroActividadanimalestate;
  readonly animalFicha: AnimalFichaState;

  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  lote,
  animalLote,
  cargue,
  detalleCargue,
  errorCargue,
  tipoParametro,
  parametroDominio,
  animalPastoreo,
  annimal,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
  finca,
  potrero,
  persona,
  potreroActividad,
  animal,
  parametros,
  animalCostos,
  animalEvento,
  animalPeso,
  animalImagen,
  animalVacunas,
  potreroActividadAnimal,
  animalFicha,
});

export default rootReducer;
