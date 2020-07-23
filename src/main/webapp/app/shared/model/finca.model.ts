import { IPotrero } from 'app/shared/model/potrero.model';

export interface IFinca {
  id?: number;
  nombre?: string;
  area?: number;
  potreros?: IPotrero[];
}

export const defaultValue: Readonly<IFinca> = {};
