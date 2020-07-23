import { IAnimal } from 'app/shared/model/animal.model';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';

export interface IPotreroActividadAnimal {
  id?: number;
  animal?: IAnimal;
  potreroActividad?: IPotreroActividad;
}

export const defaultValue: Readonly<IPotreroActividadAnimal> = {};
