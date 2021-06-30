import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IParametroDominio } from 'app/shared/model/parametro-dominio.model';
import { getEntities as getParametroDominios } from 'app/entities/parametro-dominio/parametro-dominio.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tipo-parametro.reducer';
import { ITipoParametro } from 'app/shared/model/tipo-parametro.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TipoParametroUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const parametroDominios = useAppSelector(state => state.parametroDominio.entities);
  const tipoParametroEntity = useAppSelector(state => state.tipoParametro.entity);
  const loading = useAppSelector(state => state.tipoParametro.loading);
  const updating = useAppSelector(state => state.tipoParametro.updating);
  const updateSuccess = useAppSelector(state => state.tipoParametro.updateSuccess);

  const handleClose = () => {
    props.history.push('/tipo-parametro');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getParametroDominios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tipoParametroEntity,
      ...values,
      tipaId: parametroDominios.find(it => it.id.toString() === values.tipaIdId.toString()),
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
          ...tipoParametroEntity,
          tipaIdId: tipoParametroEntity?.tipaId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.tipoParametro.home.createOrEditLabel" data-cy="TipoParametroCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.tipoParametro.home.createOrEditLabel">Create or edit a TipoParametro</Translate>
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
                  id="tipo-parametro-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.tipoParametro.tipaDescripcion')}
                id="tipo-parametro-tipaDescripcion"
                name="tipaDescripcion"
                data-cy="tipaDescripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="tipo-parametro-tipaId"
                name="tipaIdId"
                data-cy="tipaId"
                label={translate('agrofincaApp.tipoParametro.tipaId')}
                type="select"
              >
                <option value="" key="0" />
                {parametroDominios
                  ? parametroDominios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-parametro" replace color="info">
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

export default TipoParametroUpdate;
