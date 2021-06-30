import dayjs from 'dayjs';
import { IAnimal } from 'app/shared/model/animal.model';
import { IParametros } from 'app/shared/model/parametros.model';

export interface IAnimalEvento {
  id?: number;
  fecha?: string | null;
  animal?: IAnimal | null;
  evento?: IParametros | null;
}

export const defaultValue: Readonly<IAnimalEvento> = {};
