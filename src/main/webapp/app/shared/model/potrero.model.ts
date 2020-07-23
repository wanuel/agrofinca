import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { IFinca } from 'app/shared/model/finca.model';

export interface IPotrero {
  id?: number;
  nombre?: string;
  descripcion?: string;
  pasto?: string;
  area?: number;
  actividades?: IPotreroActividad[];
  finca?: IFinca;
}

export const defaultValue: Readonly<IPotrero> = {};
