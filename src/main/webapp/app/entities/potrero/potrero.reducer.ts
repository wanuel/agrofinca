import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPotrero, defaultValue } from 'app/shared/model/potrero.model';

export const ACTION_TYPES = {
  FETCH_POTRERO_LIST: 'potrero/FETCH_POTRERO_LIST',
  FETCH_POTRERO_LIST_ALL: 'potrero/FETCH_POTRERO_LIST_ALL',
  FETCH_POTRERO: 'potrero/FETCH_POTRERO',
  CREATE_POTRERO: 'potrero/CREATE_POTRERO',
  UPDATE_POTRERO: 'potrero/UPDATE_POTRERO',
  DELETE_POTRERO: 'potrero/DELETE_POTRERO',
  RESET: 'potrero/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPotrero>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PotreroState = Readonly<typeof initialState>;

// Reducer

export default (state: PotreroState = initialState, action): PotreroState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POTRERO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POTRERO_LIST_ALL):
    case REQUEST(ACTION_TYPES.FETCH_POTRERO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POTRERO):
    case REQUEST(ACTION_TYPES.UPDATE_POTRERO):
    case REQUEST(ACTION_TYPES.DELETE_POTRERO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POTRERO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POTRERO_LIST_ALL):
    case FAILURE(ACTION_TYPES.FETCH_POTRERO):
    case FAILURE(ACTION_TYPES.CREATE_POTRERO):
    case FAILURE(ACTION_TYPES.UPDATE_POTRERO):
    case FAILURE(ACTION_TYPES.DELETE_POTRERO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTRERO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTRERO_LIST_ALL):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTRERO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POTRERO):
    case SUCCESS(ACTION_TYPES.UPDATE_POTRERO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POTRERO):
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

const apiUrl = 'api/potreros';
const apiUrlAll = 'api/potrerosAll';

// Actions

export const getEntities: ICrudGetAllAction<IPotrero> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POTRERO_LIST,
    payload: axios.get<IPotrero>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntitiesAll: ICrudGetAllAction<IPotrero> = () => {
  const requestUrl = `${apiUrlAll}${''}`;
  return {
    type: ACTION_TYPES.FETCH_POTRERO_LIST_ALL,
    payload: axios.get<IPotrero>(`${requestUrl}${'?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPotrero> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POTRERO,
    payload: axios.get<IPotrero>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPotrero> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POTRERO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPotrero> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POTRERO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPotrero> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POTRERO,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
