import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './persona.reducer';
import { IPersona } from 'app/shared/model/persona.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personaEntity = useAppSelector(state => state.persona.entity);
  const loading = useAppSelector(state => state.persona.loading);
  const updating = useAppSelector(state => state.persona.updating);
  const updateSuccess = useAppSelector(state => state.persona.updateSuccess);

  const handleClose = () => {
    props.history.push('/persona' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...personaEntity,
      ...values,
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
          ...personaEntity,
          tipoDocumento: 'CC',
          genero: 'MASCULINO',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.persona.home.createOrEditLabel" data-cy="PersonaCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.persona.home.createOrEditLabel">Create or edit a Persona</Translate>
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
                  id="persona-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.persona.tipoDocumento')}
                id="persona-tipoDocumento"
                name="tipoDocumento"
                data-cy="tipoDocumento"
                type="select"
              >
                <option value="CC">{translate('agrofincaApp.TIPODOCUMENTO.CC')}</option>
                <option value="TI">{translate('agrofincaApp.TIPODOCUMENTO.TI')}</option>
                <option value="CE">{translate('agrofincaApp.TIPODOCUMENTO.CE')}</option>
                <option value="NIT">{translate('agrofincaApp.TIPODOCUMENTO.NIT')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('agrofincaApp.persona.numDocuemnto')}
                id="persona-numDocuemnto"
                name="numDocuemnto"
                data-cy="numDocuemnto"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.primerNombre')}
                id="persona-primerNombre"
                name="primerNombre"
                data-cy="primerNombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.segundoNombre')}
                id="persona-segundoNombre"
                name="segundoNombre"
                data-cy="segundoNombre"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.primerApellido')}
                id="persona-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.segundoApellido')}
                id="persona-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.fechaNacimiento')}
                id="persona-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="date"
              />
              <ValidatedField
                label={translate('agrofincaApp.persona.genero')}
                id="persona-genero"
                name="genero"
                data-cy="genero"
                type="select"
              >
                <option value="MASCULINO">{translate('agrofincaApp.GENERO.MASCULINO')}</option>
                <option value="FEMENINO">{translate('agrofincaApp.GENERO.FEMENINO')}</option>
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/persona" replace color="info">
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

export default PersonaUpdate;
