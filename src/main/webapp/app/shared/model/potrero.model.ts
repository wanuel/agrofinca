import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { IFinca } from 'app/shared/model/finca.model';

export interface IPotrero {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  pasto?: string | null;
  area?: number | null;
  actividades?: IPotreroActividad[] | null;
  finca?: IFinca | null;
}

export const defaultValue: Readonly<IPotrero> = {};
