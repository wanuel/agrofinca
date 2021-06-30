import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IParametros } from 'app/shared/model/parametros.model';
import { getEntities as getParametros } from 'app/entities/parametros/parametros.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { getEntities as getPotreroActividads } from 'app/entities/potrero-actividad/potrero-actividad.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal.reducer';
import { IAnimal } from 'app/shared/model/animal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const parametros = useAppSelector(state => state.parametros.entities);
  const potreroActividads = useAppSelector(state => state.potreroActividad.entities);
  const animalEntity = useAppSelector(state => state.animal.entity);
  const loading = useAppSelector(state => state.animal.loading);
  const updating = useAppSelector(state => state.animal.updating);
  const updateSuccess = useAppSelector(state => state.animal.updateSuccess);

  const handleClose = () => {
    props.history.push('/animal' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getParametros({}));
    dispatch(getPotreroActividads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...animalEntity,
      ...values,
      tipo: parametros.find(it => it.id.toString() === values.tipoId.toString()),
      raza: parametros.find(it => it.id.toString() === values.razaId.toString()),
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
          ...animalEntity,
          hierro: 'S',
          sexo: 'MACHO',
          castrado: 'S',
          estado: 'VIVO',
          tipoId: animalEntity?.tipo?.id,
          razaId: animalEntity?.raza?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.animal.home.createOrEditLabel" data-cy="AnimalCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.animal.home.createOrEditLabel">Create or edit a Animal</Translate>
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
                  id="animal-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.animal.nombre')}
                id="animal-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.animal.caracterizacion')}
                id="animal-caracterizacion"
                name="caracterizacion"
                data-cy="caracterizacion"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.animal.hierro')}
                id="animal-hierro"
                name="hierro"
                data-cy="hierro"
                type="select"
              >
                <option value="S">{translate('agrofincaApp.SINO.S')}</option>
                <option value="N">{translate('agrofincaApp.SINO.N')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('agrofincaApp.animal.fechaNacimiento')}
                id="animal-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="date"
              />
              <ValidatedField
                label={translate('agrofincaApp.animal.fechaCompra')}
                id="animal-fechaCompra"
                name="fechaCompra"
                data-cy="fechaCompra"
                type="date"
              />
              <ValidatedField label={translate('agrofincaApp.animal.sexo')} id="animal-sexo" name="sexo" data-cy="sexo" type="select">
                <option value="MACHO">{translate('agrofincaApp.SEXO.MACHO')}</option>
                <option value="HEMBRA">{translate('agrofincaApp.SEXO.HEMBRA')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('agrofincaApp.animal.castrado')}
                id="animal-castrado"
                name="castrado"
                data-cy="castrado"
                type="select"
              >
                <option value="S">{translate('agrofincaApp.SINO.S')}</option>
                <option value="N">{translate('agrofincaApp.SINO.N')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('agrofincaApp.animal.fechaCastracion')}
                id="animal-fechaCastracion"
                name="fechaCastracion"
                data-cy="fechaCastracion"
                type="date"
              />
              <ValidatedField
                label={translate('agrofincaApp.animal.estado')}
                id="animal-estado"
                name="estado"
                data-cy="estado"
                type="select"
              >
                <option value="VIVO">{translate('agrofincaApp.ESTADOANIMAL.VIVO')}</option>
                <option value="MUERTO">{translate('agrofincaApp.ESTADOANIMAL.MUERTO')}</option>
              </ValidatedField>
              <ValidatedField id="animal-tipo" name="tipoId" data-cy="tipo" label={translate('agrofincaApp.animal.tipo')} type="select">
                <option value="" key="0" />
                {parametros
                  ? parametros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="animal-raza" name="razaId" data-cy="raza" label={translate('agrofincaApp.animal.raza')} type="select">
                <option value="" key="0" />
                {parametros
                  ? parametros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/animal" replace color="info">
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

export default AnimalUpdate;
