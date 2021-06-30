import dayjs from 'dayjs';
import { IAnnimal } from 'app/shared/model/annimal.model';
import { ILote } from 'app/shared/model/lote.model';

export interface IAnimalLote {
  id?: number;
  fechaEntrada?: string;
  fechaSalida?: string | null;
  animal?: IAnnimal | null;
  lote?: ILote | null;
}

export const defaultValue: Readonly<IAnimalLote> = {};
