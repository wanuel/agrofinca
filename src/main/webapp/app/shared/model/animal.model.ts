import dayjs from 'dayjs';
import { IAnimalImagen } from 'app/shared/model/animal-imagen.model';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';
import { IAnimalPeso } from 'app/shared/model/animal-peso.model';
import { IAnimalEvento } from 'app/shared/model/animal-evento.model';
import { IAnimalCostos } from 'app/shared/model/animal-costos.model';
import { IParametros } from 'app/shared/model/parametros.model';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { SINO } from 'app/shared/model/enumerations/sino.model';
import { SEXO } from 'app/shared/model/enumerations/sexo.model';
import { ESTADOANIMAL } from 'app/shared/model/enumerations/estadoanimal.model';

export interface IAnimal {
  id?: number;
  nombre?: string | null;
  caracterizacion?: string | null;
  hierro?: SINO | null;
  fechaNacimiento?: string | null;
  fechaCompra?: string | null;
  sexo?: SEXO;
  castrado?: SINO;
  fechaCastracion?: string | null;
  estado?: ESTADOANIMAL;
  imagenes?: IAnimalImagen[] | null;
  vacunas?: IAnimalVacunas[] | null;
  pesos?: IAnimalPeso[] | null;
  eventos?: IAnimalEvento[] | null;
  costos?: IAnimalCostos[] | null;
  tipo?: IParametros | null;
  raza?: IParametros | null;
  potreros?: IPotreroActividad[] | null;
}

export const defaultValue: Readonly<IAnimal> = {};
