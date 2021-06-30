import { IErrorCargue } from 'app/shared/model/error-cargue.model';

export interface IDetalleCargue {
  id?: number;
  decaCup?: string;
  decaEstado?: string;
  decaJSON?: string;
  decaId?: IErrorCargue | null;
}

export const defaultValue: Readonly<IDetalleCargue> = {};
