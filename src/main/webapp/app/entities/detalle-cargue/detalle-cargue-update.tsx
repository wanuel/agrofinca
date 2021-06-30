import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IErrorCargue } from 'app/shared/model/error-cargue.model';
import { getEntities as getErrorCargues } from 'app/entities/error-cargue/error-cargue.reducer';
import { getEntity, updateEntity, createEntity, reset } from './detalle-cargue.reducer';
import { IDetalleCargue } from 'app/shared/model/detalle-cargue.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DetalleCargueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const errorCargues = useAppSelector(state => state.errorCargue.entities);
  const detalleCargueEntity = useAppSelector(state => state.detalleCargue.entity);
  const loading = useAppSelector(state => state.detalleCargue.loading);
  const updating = useAppSelector(state => state.detalleCargue.updating);
  const updateSuccess = useAppSelector(state => state.detalleCargue.updateSuccess);

  const handleClose = () => {
    props.history.push('/detalle-cargue');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getErrorCargues({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...detalleCargueEntity,
      ...values,
      decaId: errorCargues.find(it => it.id.toString() === values.decaIdId.toString()),
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
          ...detalleCargueEntity,
          decaIdId: detalleCargueEntity?.decaId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.detalleCargue.home.createOrEditLabel" data-cy="DetalleCargueCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.detalleCargue.home.createOrEditLabel">Create or edit a DetalleCargue</Translate>
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
                  id="detalle-cargue-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.detalleCargue.decaCup')}
                id="detalle-cargue-decaCup"
                name="decaCup"
                data-cy="decaCup"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.detalleCargue.decaEstado')}
                id="detalle-cargue-decaEstado"
                name="decaEstado"
                data-cy="decaEstado"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.detalleCargue.decaJSON')}
                id="detalle-cargue-decaJSON"
                name="decaJSON"
                data-cy="decaJSON"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="detalle-cargue-decaId"
                name="decaIdId"
                data-cy="decaId"
                label={translate('agrofincaApp.detalleCargue.decaId')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/detalle-cargue" replace color="info">
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

export default DetalleCargueUpdate;
