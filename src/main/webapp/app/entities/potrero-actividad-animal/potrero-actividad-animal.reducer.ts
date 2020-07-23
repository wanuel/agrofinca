import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPotreroActividadAnimal, defaultValue } from 'app/shared/model/potrero-actividad-animal.model';

export const ACTION_TYPES = {
  FETCH_POTREROACTIVIDADANIMAL_LIST: 'potreroActividadAnimal/FETCH_POTREROACTIVIDADANIMAL_LIST',
  FETCH_POTREROACTIVIDADANIMAL: 'potreroActividadAnimal/FETCH_POTREROACTIVIDADANIMAL',
  CREATE_POTREROACTIVIDADANIMAL: 'potreroActividadAnimal/CREATE_POTREROACTIVIDADANIMAL',
  UPDATE_POTREROACTIVIDADANIMAL: 'potreroActividadAnimal/UPDATE_POTREROACTIVIDADANIMAL',
  DELETE_POTREROACTIVIDADANIMAL: 'potreroActividadAnimal/DELETE_POTREROACTIVIDADANIMAL',
  RESET: 'potreroActividadAnimal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPotreroActividadAnimal>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PotreroActividadAnimalState = Readonly<typeof initialState>;

// Reducer

export default (state: PotreroActividadAnimalState = initialState, action): PotreroActividadAnimalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POTREROACTIVIDADANIMAL):
    case REQUEST(ACTION_TYPES.UPDATE_POTREROACTIVIDADANIMAL):
    case REQUEST(ACTION_TYPES.DELETE_POTREROACTIVIDADANIMAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL):
    case FAILURE(ACTION_TYPES.CREATE_POTREROACTIVIDADANIMAL):
    case FAILURE(ACTION_TYPES.UPDATE_POTREROACTIVIDADANIMAL):
    case FAILURE(ACTION_TYPES.DELETE_POTREROACTIVIDADANIMAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POTREROACTIVIDADANIMAL):
    case SUCCESS(ACTION_TYPES.UPDATE_POTREROACTIVIDADANIMAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POTREROACTIVIDADANIMAL):
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

const apiUrl = 'api/potrero-actividad-animals';

// Actions

export const getEntities: ICrudGetAllAction<IPotreroActividadAnimal> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL_LIST,
    payload: axios.get<IPotreroActividadAnimal>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPotreroActividadAnimal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POTREROACTIVIDADANIMAL,
    payload: axios.get<IPotreroActividadAnimal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPotreroActividadAnimal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POTREROACTIVIDADANIMAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IPotreroActividadAnimal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POTREROACTIVIDADANIMAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPotreroActividadAnimal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POTREROACTIVIDADANIMAL,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
