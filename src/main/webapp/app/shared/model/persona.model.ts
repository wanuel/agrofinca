import dayjs from 'dayjs';
import { TIPODOCUMENTO } from 'app/shared/model/enumerations/tipodocumento.model';
import { GENERO } from 'app/shared/model/enumerations/genero.model';

export interface IPersona {
  id?: number;
  tipoDocumento?: TIPODOCUMENTO;
  numDocuemnto?: number | null;
  primerNombre?: string;
  segundoNombre?: string | null;
  primerApellido?: string | null;
  segundoApellido?: string | null;
  fechaNacimiento?: string | null;
  genero?: GENERO | null;
}

export const defaultValue: Readonly<IPersona> = {};
