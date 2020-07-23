import { IAnimalCostos } from 'app/shared/model/animal-costos.model';
import { IAnimalEvento } from 'app/shared/model/animal-evento.model';
import { IAnimal } from 'app/shared/model/animal.model';
import { IAnimalPeso } from 'app/shared/model/animal-peso.model';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';

export interface IParametros {
  id?: number;
  descripcion?: string;
  costos?: IAnimalCostos[];
  eventos?: IAnimalEvento[];
  animalesTipos?: IAnimal[];
  animalesRazas?: IAnimal[];
  pesos?: IAnimalPeso[];
  vacunas?: IAnimalVacunas[];
  parametros?: IParametros[];
  padre?: IParametros;
}

export const defaultValue: Readonly<IParametros> = {};
