import { IParametroDominio } from 'app/shared/model/parametro-dominio.model';

export interface ITipoParametro {
  id?: number;
  tipaDescripcion?: string;
  tipaId?: IParametroDominio | null;
}

export const defaultValue: Readonly<ITipoParametro> = {};
