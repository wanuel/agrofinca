import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */
import potrero, { PotreroState } from 'app/entities/potrero/potrero.reducer';
// prettier-ignore
import finca, { FincaState } from 'app/entities/finca/finca.reducer';
// prettier-ignore
import persona, { PersonaState } from 'app/entities/persona/persona.reducer';
import potreroActividad, { PotreroActividadState } from 'app/entities/potrero-actividad/potrero-actividad.reducer';
import animal, { AnimalState } from 'app/entities/animal/animal.reducer';
import parametros, { ParametrosState } from 'app/entities/parametros/parametros.reducer';
import animalCostos, { AnimalCostosState } from 'app/entities/animal-costos/animal-costos.reducer';
import animalEvento, { AnimalEventoState } from 'app/entities/animal-evento/animal-evento.reducer';
import animalPeso, { AnimalPesoState } from 'app/entities/animal-peso/animal-peso.reducer';
import animalImagen, { AnimalImagenState } from 'app/entities/animal-imagen/animal-imagen.reducer';
import animalVacunas, { AnimalVacunasState } from 'app/entities/animal-vacunas/animal-vacunas.reducer';
import potreroActividadAnimal, {
  PotreroActividadAnimalState,
} from 'app/entities/potrero-actividad-animal/potrero-actividad-animal.reducer';
import animalFicha, { AnimalFichaState } from 'app/entities/animal-ficha/animal-ficha.reducer';

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly finca: FincaState;
  readonly potrero: PotreroState;
  readonly persona: PersonaState;
  readonly potreroActividad: PotreroActividadState;
  readonly animal: AnimalState;
  readonly parametros: ParametrosState;
  readonly animalCostos: AnimalCostosState;
  readonly animalEvento: AnimalEventoState;
  readonly animalPeso: AnimalPesoState;
  readonly animalImagen: AnimalImagenState;
  readonly animalVacunas: AnimalVacunasState;
  readonly potreroActividadAnimal: PotreroActividadAnimalState;
  readonly animalFicha: AnimalFichaState;

  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
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
