import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IPotrero } from 'app/shared/model/potrero.model';
import { getEntities as getPotreros } from 'app/entities/potrero/potrero.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potrero-actividad.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroActividadUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const animals = useAppSelector(state => state.animal.entities);
  const potreros = useAppSelector(state => state.potrero.entities);
  const potreroActividadEntity = useAppSelector(state => state.potreroActividad.entity);
  const loading = useAppSelector(state => state.potreroActividad.loading);
  const updating = useAppSelector(state => state.potreroActividad.updating);
  const updateSuccess = useAppSelector(state => state.potreroActividad.updateSuccess);

  const handleClose = () => {
    props.history.push('/potrero-actividad' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAnimals({}));
    dispatch(getPotreros({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...potreroActividadEntity,
      ...values,
      animals: mapIdList(values.animals),
      potrero: potreros.find(it => it.id.toString() === values.potreroId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...potreroActividadEntity,
          ocupado: 'S',
          animals: potreroActividadEntity?.animals?.map(e => e.id.toString()),
          potreroId: potreroActividadEntity?.potrero?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.potreroActividad.home.createOrEditLabel" data-cy="PotreroActividadCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.potreroActividad.home.createOrEditLabel">Create or edit a PotreroActividad</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="potrero-actividad-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.fechaIngreso')}
                id="potrero-actividad-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.fechaSalida')}
                id="potrero-actividad-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.cantidadBovinos')}
                id="potrero-actividad-cantidadBovinos"
                name="cantidadBovinos"
                data-cy="cantidadBovinos"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.cantidadEquinos')}
                id="potrero-actividad-cantidadEquinos"
                name="cantidadEquinos"
                data-cy="cantidadEquinos"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.cantidadMulares')}
                id="potrero-actividad-cantidadMulares"
                name="cantidadMulares"
                data-cy="cantidadMulares"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.fechaLimpia')}
                id="potrero-actividad-fechaLimpia"
                name="fechaLimpia"
                data-cy="fechaLimpia"
                type="date"
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.diasDescanso')}
                id="potrero-actividad-diasDescanso"
                name="diasDescanso"
                data-cy="diasDescanso"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.diasCarga')}
                id="potrero-actividad-diasCarga"
                name="diasCarga"
                data-cy="diasCarga"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.ocupado')}
                id="potrero-actividad-ocupado"
                name="ocupado"
                data-cy="ocupado"
                type="select"
              >
                <option value="S">{translate('agrofincaApp.SINO.S')}</option>
                <option value="N">{translate('agrofincaApp.SINO.N')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('agrofincaApp.potreroActividad.animal')}
                id="potrero-actividad-animal"
                data-cy="animal"
                type="select"
                multiple
                name="animals"
              >
                <option value="" key="0" />
                {animals
                  ? animals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="potrero-actividad-potrero"
                name="potreroId"
                data-cy="potrero"
                label={translate('agrofincaApp.potreroActividad.potrero')}
                type="select"
              >
                <option value="" key="0" />
                {potreros
                  ? potreros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/potrero-actividad" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PotreroActividadUpdate;
