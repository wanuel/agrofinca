import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPotreroActividad, defaultValue } from 'app/shared/model/potrero-actividad.model';

export const ACTION_TYPES = {
  FETCH_POTREROACTIVIDAD_LIST: 'potreroActividad/FETCH_POTREROACTIVIDAD_LIST',
  FETCH_POTREROACTIVIDAD: 'potreroActividad/FETCH_POTREROACTIVIDAD',
  CREATE_POTREROACTIVIDAD: 'potreroActividad/CREATE_POTREROACTIVIDAD',
  UPDATE_POTREROACTIVIDAD: 'potreroActividad/UPDATE_POTREROACTIVIDAD',
  DELETE_POTREROACTIVIDAD: 'potreroActividad/DELETE_POTREROACTIVIDAD',
  RESET: 'potreroActividad/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPotreroActividad>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PotreroActividadState = Readonly<typeof initialState>;

// Reducer

export default (state: PotreroActividadState = initialState, action): PotreroActividadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POTREROACTIVIDAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POTREROACTIVIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POTREROACTIVIDAD):
    case REQUEST(ACTION_TYPES.UPDATE_POTREROACTIVIDAD):
    case REQUEST(ACTION_TYPES.DELETE_POTREROACTIVIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POTREROACTIVIDAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POTREROACTIVIDAD):
    case FAILURE(ACTION_TYPES.CREATE_POTREROACTIVIDAD):
    case FAILURE(ACTION_TYPES.UPDATE_POTREROACTIVIDAD):
    case FAILURE(ACTION_TYPES.DELETE_POTREROACTIVIDAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTREROACTIVIDAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTREROACTIVIDAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POTREROACTIVIDAD):
    case SUCCESS(ACTION_TYPES.UPDATE_POTREROACTIVIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POTREROACTIVIDAD):
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

const apiUrl = 'api/potrero-actividads';

// Actions

export const getEntities: ICrudGetAllAction<IPotreroActividad> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POTREROACTIVIDAD_LIST,
    payload: axios.get<IPotreroActividad>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPotreroActividad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POTREROACTIVIDAD,
    payload: axios.get<IPotreroActividad>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPotreroActividad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POTREROACTIVIDAD,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPotreroActividad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POTREROACTIVIDAD,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPotreroActividad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POTREROACTIVIDAD,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
