import { Moment } from 'moment';
import { ILote } from 'app/shared/model/lote.model';
import { IPotrero } from 'app/shared/model/potrero.model';
import { SINO } from 'app/shared/model/enumerations/sino.model';

export interface IPotreroActividad {
  id?: number;
  fechaIngreso?: string;
  fechaSalida?: string;
  cantidadBovinos?: number;
  cantidadEquinos?: number;
  cantidadMulares?: number;
  fechaLimpia?: string;
  diasDescanso?: number;
  diasCarga?: number;
  ocupado?: SINO;
  lote?: ILote;
  potrero?: IPotrero;
}

export const defaultValue: Readonly<IPotreroActividad> = {};
