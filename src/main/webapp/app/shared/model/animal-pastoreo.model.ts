import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { IAnimal } from 'app/shared/model/animal.model';

export interface IAnimalPastoreo {
  animales?: IAnimal[];
  pastoreos?: IPotreroActividad[];
}

export const defaultValue: Readonly<IAnimalPastoreo> = {};
