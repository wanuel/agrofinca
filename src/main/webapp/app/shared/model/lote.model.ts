import { Moment } from 'moment';
import { IAnimalLote } from 'app/shared/model/animal-lote.model';
import { TipoLoteEnum } from 'app/shared/model/enumerations/tipo-lote-enum.model';

export interface ILote {
  id?: number;
  nombre?: string;
  tipo?: TipoLoteEnum;
  fecha?: string;
  numeroAnimales?: number;
  animales?: IAnimalLote[];
}

export const defaultValue: Readonly<ILote> = {};
