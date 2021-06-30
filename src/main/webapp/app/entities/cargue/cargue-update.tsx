import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDetalleCargue } from 'app/shared/model/detalle-cargue.model';
import { getEntities as getDetalleCargues } from 'app/entities/detalle-cargue/detalle-cargue.reducer';
import { IErrorCargue } from 'app/shared/model/error-cargue.model';
import { getEntities as getErrorCargues } from 'app/entities/error-cargue/error-cargue.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cargue.reducer';
import { ICargue } from 'app/shared/model/cargue.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CargueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const detalleCargues = useAppSelector(state => state.detalleCargue.entities);
  const errorCargues = useAppSelector(state => state.errorCargue.entities);
  const cargueEntity = useAppSelector(state => state.cargue.entity);
  const loading = useAppSelector(state => state.cargue.loading);
  const updating = useAppSelector(state => state.cargue.updating);
  const updateSuccess = useAppSelector(state => state.cargue.updateSuccess);

  const handleClose = () => {
    props.history.push('/cargue');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDetalleCargues({}));
    dispatch(getErrorCargues({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaCreacion = convertDateTimeToServer(values.fechaCreacion);
    values.fechaModificacion = convertDateTimeToServer(values.fechaModificacion);

    const entity = {
      ...cargueEntity,
      ...values,
      cargId: detalleCargues.find(it => it.id.toString() === values.cargIdId.toString()),
      cargId: errorCargues.find(it => it.id.toString() === values.cargIdId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaCreacion: displayDefaultDateTime(),
          fechaModificacion: displayDefaultDateTime(),
        }
      : {
          ...cargueEntity,
          fechaCreacion: convertDateTimeFromServer(cargueEntity.fechaCreacion),
          fechaModificacion: convertDateTimeFromServer(cargueEntity.fechaModificacion),
          cargIdId: cargueEntity?.cargId?.id,
          cargIdId: cargueEntity?.cargId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.cargue.home.createOrEditLabel" data-cy="CargueCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.cargue.home.createOrEditLabel">Create or edit a Cargue</Translate>
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
                  id="cargue-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargNroRegistros')}
                id="cargue-cargNroRegistros"
                name="cargNroRegistros"
                data-cy="cargNroRegistros"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargJson')}
                id="cargue-cargJson"
                name="cargJson"
                data-cy="cargJson"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargEntidad')}
                id="cargue-cargEntidad"
                name="cargEntidad"
                data-cy="cargEntidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargNombreArchivo')}
                id="cargue-cargNombreArchivo"
                name="cargNombreArchivo"
                data-cy="cargNombreArchivo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargEstado')}
                id="cargue-cargEstado"
                name="cargEstado"
                data-cy="cargEstado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargTipo')}
                id="cargue-cargTipo"
                name="cargTipo"
                data-cy="cargTipo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargEsReproceso')}
                id="cargue-cargEsReproceso"
                name="cargEsReproceso"
                data-cy="cargEsReproceso"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.cargHash')}
                id="cargue-cargHash"
                name="cargHash"
                data-cy="cargHash"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.usuarioCreacion')}
                id="cargue-usuarioCreacion"
                name="usuarioCreacion"
                data-cy="usuarioCreacion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.fechaCreacion')}
                id="cargue-fechaCreacion"
                name="fechaCreacion"
                data-cy="fechaCreacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.usuarioModificacion')}
                id="cargue-usuarioModificacion"
                name="usuarioModificacion"
                data-cy="usuarioModificacion"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.cargue.fechaModificacion')}
                id="cargue-fechaModificacion"
                name="fechaModificacion"
                data-cy="fechaModificacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="cargue-cargId"
                name="cargIdId"
                data-cy="cargId"
                label={translate('agrofincaApp.cargue.cargId')}
                type="select"
              >
                <option value="" key="0" />
                {detalleCargues
                  ? detalleCargues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="cargue-cargId"
                name="cargIdId"
                data-cy="cargId"
                label={translate('agrofincaApp.cargue.cargId')}
                type="select"
              >
                <option value="" key="0" />
                {errorCargues
                  ? errorCargues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cargue" replace color="info">
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

export default CargueUpdate;
