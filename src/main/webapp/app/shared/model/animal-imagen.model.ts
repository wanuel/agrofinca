import dayjs from 'dayjs';
import { IAnimal } from 'app/shared/model/animal.model';

export interface IAnimalImagen {
  id?: number;
  fecha?: string;
  nota?: string | null;
  imagenContentType?: string;
  imagen?: string;
  animal?: IAnimal | null;
}

export const defaultValue: Readonly<IAnimalImagen> = {};
