import { Moment } from 'moment';
import { IAnimal } from 'app/shared/model/animal.model';
import { IParametros } from 'app/shared/model/parametros.model';

export interface IAnimalVacunas {
  id?: number;
  fecha?: string;
  nombre?: string;
  laboratorio?: string;
  dosis?: string;
  animal?: IAnimal;
  tipo?: IParametros;
}

export const defaultValue: Readonly<IAnimalVacunas> = {};
