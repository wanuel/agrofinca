import { IAnimalLote } from 'app/shared/model/animal-lote.model';

export interface IAnnimal {
  id?: number;
  nombre?: string;
  lotes?: IAnimalLote[] | null;
}

export const defaultValue: Readonly<IAnnimal> = {};
