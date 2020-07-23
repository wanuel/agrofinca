import { Moment } from 'moment';
import { IAnimal } from 'app/shared/model/animal.model';
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
  animals?: IAnimal[];
  potrero?: IPotrero;
}

export const defaultValue: Readonly<IPotreroActividad> = {};
