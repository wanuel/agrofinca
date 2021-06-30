import { IPotrero } from 'app/shared/model/potrero.model';

export interface IFinca {
  id?: number;
  nombre?: string;
  area?: number | null;
  potreros?: IPotrero[] | null;
}

export const defaultValue: Readonly<IFinca> = {};
