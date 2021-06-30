import { IAnimal } from 'app/shared/model/animal.model';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';

export interface IPotreroActividadAnimal {
  id?: number;
  animalId?: IAnimal | null;
  potreroActividadId?: IPotreroActividad | null;
}

export const defaultValue: Readonly<IPotreroActividadAnimal> = {};
