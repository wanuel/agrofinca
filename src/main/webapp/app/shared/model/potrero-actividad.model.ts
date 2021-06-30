import dayjs from 'dayjs';
import { IAnimal } from 'app/shared/model/animal.model';
import { IPotrero } from 'app/shared/model/potrero.model';
import { SINO } from 'app/shared/model/enumerations/sino.model';

export interface IPotreroActividad {
  id?: number;
  fechaIngreso?: string;
  fechaSalida?: string | null;
  cantidadBovinos?: number;
  cantidadEquinos?: number;
  cantidadMulares?: number;
  fechaLimpia?: string | null;
  diasDescanso?: number | null;
  diasCarga?: number | null;
  ocupado?: SINO;
  animals?: IAnimal[] | null;
  potrero?: IPotrero | null;
}

export const defaultValue: Readonly<IPotreroActividad> = {};
