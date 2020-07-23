import { Moment } from 'moment';
import { IAnimal } from 'app/shared/model/animal.model';
import { IParametros } from 'app/shared/model/parametros.model';

export interface IAnimalEvento {
  id?: number;
  fecha?: string;
  animal?: IAnimal;
  evento?: IParametros;
}

export const defaultValue: Readonly<IAnimalEvento> = {};
