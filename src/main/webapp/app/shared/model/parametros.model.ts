import { IAnimalCostos } from 'app/shared/model/animal-costos.model';
import { IAnimalEvento } from 'app/shared/model/animal-evento.model';
import { IAnimal } from 'app/shared/model/animal.model';
import { IAnimalPeso } from 'app/shared/model/animal-peso.model';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';

export interface IParametros {
  id?: number;
  descripcion?: string;
  costos?: IAnimalCostos[] | null;
  eventos?: IAnimalEvento[] | null;
  animalesTipos?: IAnimal[] | null;
  animalesRazas?: IAnimal[] | null;
  pesos?: IAnimalPeso[] | null;
  vacunas?: IAnimalVacunas[] | null;
  parametros?: IParametros[] | null;
  padre?: IParametros | null;
}

export const defaultValue: Readonly<IParametros> = {};
