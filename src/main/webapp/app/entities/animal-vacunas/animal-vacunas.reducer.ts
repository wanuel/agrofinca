import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAnimalVacunas, defaultValue } from 'app/shared/model/animal-vacunas.model';

export const ACTION_TYPES = {
  FETCH_ANIMALVACUNAS_LIST: 'animalVacunas/FETCH_ANIMALVACUNAS_LIST',
  FETCH_ANIMALVACUNAS: 'animalVacunas/FETCH_ANIMALVACUNAS',
  CREATE_ANIMALVACUNAS: 'animalVacunas/CREATE_ANIMALVACUNAS',
  UPDATE_ANIMALVACUNAS: 'animalVacunas/UPDATE_ANIMALVACUNAS',
  DELETE_ANIMALVACUNAS: 'animalVacunas/DELETE_ANIMALVACUNAS',
  RESET: 'animalVacunas/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAnimalVacunas>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AnimalVacunasState = Readonly<typeof initialState>;

// Reducer

export default (state: AnimalVacunasState = initialState, action): AnimalVacunasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ANIMALVACUNAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ANIMALVACUNAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ANIMALVACUNAS):
    case REQUEST(ACTION_TYPES.UPDATE_ANIMALVACUNAS):
    case REQUEST(ACTION_TYPES.DELETE_ANIMALVACUNAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ANIMALVACUNAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ANIMALVACUNAS):
    case FAILURE(ACTION_TYPES.CREATE_ANIMALVACUNAS):
    case FAILURE(ACTION_TYPES.UPDATE_ANIMALVACUNAS):
    case FAILURE(ACTION_TYPES.DELETE_ANIMALVACUNAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIMALVACUNAS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIMALVACUNAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ANIMALVACUNAS):
    case SUCCESS(ACTION_TYPES.UPDATE_ANIMALVACUNAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ANIMALVACUNAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/animal-vacunas';

// Actions

export const getEntities: ICrudGetAllAction<IAnimalVacunas> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ANIMALVACUNAS_LIST,
    payload: axios.get<IAnimalVacunas>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAnimalVacunas> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ANIMALVACUNAS,
    payload: axios.get<IAnimalVacunas>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAnimalVacunas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ANIMALVACUNAS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAnimalVacunas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ANIMALVACUNAS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAnimalVacunas> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ANIMALVACUNAS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
