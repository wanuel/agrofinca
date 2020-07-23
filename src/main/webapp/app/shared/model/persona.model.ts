import { Moment } from 'moment';
import { TIPODOCUMENTO } from 'app/shared/model/enumerations/tipodocumento.model';
import { GENERO } from 'app/shared/model/enumerations/genero.model';

export interface IPersona {
  id?: number;
  tipoDocumento?: TIPODOCUMENTO;
  numDocuemnto?: number;
  primerNombre?: string;
  segundoNombre?: string;
  primerApellido?: string;
  segundoApellido?: string;
  fechaNacimiento?: string;
  genero?: GENERO;
}

export const defaultValue: Readonly<IPersona> = {};
