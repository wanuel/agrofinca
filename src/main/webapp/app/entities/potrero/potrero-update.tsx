import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFinca } from 'app/shared/model/finca.model';
import { getEntities as getFincas } from 'app/entities/finca/finca.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potrero.reducer';
import { IPotrero } from 'app/shared/model/potrero.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const fincas = useAppSelector(state => state.finca.entities);
  const potreroEntity = useAppSelector(state => state.potrero.entity);
  const loading = useAppSelector(state => state.potrero.loading);
  const updating = useAppSelector(state => state.potrero.updating);
  const updateSuccess = useAppSelector(state => state.potrero.updateSuccess);

  const handleClose = () => {
    props.history.push('/potrero' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getFincas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...potreroEntity,
      ...values,
      finca: fincas.find(it => it.id.toString() === values.fincaId.toString()),
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
          ...potreroEntity,
          fincaId: potreroEntity?.finca?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.potrero.home.createOrEditLabel" data-cy="PotreroCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.potrero.home.createOrEditLabel">Create or edit a Potrero</Translate>
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
                  id="potrero-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.potrero.nombre')}
                id="potrero-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.potrero.descripcion')}
                id="potrero-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField label={translate('agrofincaApp.potrero.pasto')} id="potrero-pasto" name="pasto" data-cy="pasto" type="text" />
              <ValidatedField label={translate('agrofincaApp.potrero.area')} id="potrero-area" name="area" data-cy="area" type="text" />
              <ValidatedField
                id="potrero-finca"
                name="fincaId"
                data-cy="finca"
                label={translate('agrofincaApp.potrero.finca')}
                type="select"
              >
                <option value="" key="0" />
                {fincas
                  ? fincas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/potrero" replace color="info">
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

export default PotreroUpdate;
