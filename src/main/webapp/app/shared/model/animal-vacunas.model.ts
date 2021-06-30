import dayjs from 'dayjs';
import { IAnimal } from 'app/shared/model/animal.model';
import { IParametros } from 'app/shared/model/parametros.model';

export interface IAnimalVacunas {
  id?: number;
  fecha?: string;
  nombre?: string;
  laboratorio?: string | null;
  dosis?: string;
  animal?: IAnimal | null;
  tipo?: IParametros | null;
}

export const defaultValue: Readonly<IAnimalVacunas> = {};
