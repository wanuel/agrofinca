import { Moment } from 'moment';
import { IAnimal } from 'app/shared/model/animal.model';
import { IParametros } from 'app/shared/model/parametros.model';

export interface IAnimalCostos {
  id?: number;
  fecha?: string;
  valor?: number;
  animal?: IAnimal;
  evento?: IParametros;
}

export const defaultValue: Readonly<IAnimalCostos> = {};
