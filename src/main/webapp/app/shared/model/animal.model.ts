import { Moment } from 'moment';
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
  nombre?: string;
  caracterizacion?: string;
  hierro?: SINO;
  fechaNacimiento?: string;
  fechaCompra?: string;
  sexo?: SEXO;
  castrado?: SINO;
  fechaCastracion?: string;
  estado?: ESTADOANIMAL;
  imagenes?: IAnimalImagen[];
  vacunas?: IAnimalVacunas[];
  pesos?: IAnimalPeso[];
  eventos?: IAnimalEvento[];
  costos?: IAnimalCostos[];
  tipo?: IParametros;
  raza?: IParametros;
  potreros?: IPotreroActividad[];
}

export const defaultValue: Readonly<IAnimal> = {};
