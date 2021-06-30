import dayjs from 'dayjs';
import { IDetalleCargue } from 'app/shared/model/detalle-cargue.model';
import { IErrorCargue } from 'app/shared/model/error-cargue.model';

export interface ICargue {
  id?: number;
  cargNroRegistros?: number;
  cargJson?: string;
  cargEntidad?: number;
  cargNombreArchivo?: string;
  cargEstado?: string;
  cargTipo?: string;
  cargEsReproceso?: number;
  cargHash?: string;
  usuarioCreacion?: string;
  fechaCreacion?: string;
  usuarioModificacion?: string | null;
  fechaModificacion?: string | null;
  cargId?: IDetalleCargue | null;
  cargId?: IErrorCargue | null;
}

export const defaultValue: Readonly<ICargue> = {};
