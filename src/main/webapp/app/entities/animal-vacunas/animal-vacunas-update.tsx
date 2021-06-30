import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IParametros } from 'app/shared/model/parametros.model';
import { getEntities as getParametros } from 'app/entities/parametros/parametros.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-vacunas.reducer';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalVacunasUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const animals = useAppSelector(state => state.animal.entities);
  const parametros = useAppSelector(state => state.parametros.entities);
  const animalVacunasEntity = useAppSelector(state => state.animalVacunas.entity);
  const loading = useAppSelector(state => state.animalVacunas.loading);
  const updating = useAppSelector(state => state.animalVacunas.updating);
  const updateSuccess = useAppSelector(state => state.animalVacunas.updateSuccess);

  const handleClose = () => {
    props.history.push('/animal-vacunas' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAnimals({}));
    dispatch(getParametros({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...animalVacunasEntity,
      ...values,
      animal: animals.find(it => it.id.toString() === values.animalId.toString()),
      tipo: parametros.find(it => it.id.toString() === values.tipoId.toString()),
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
          ...animalVacunasEntity,
          animalId: animalVacunasEntity?.animal?.id,
          tipoId: animalVacunasEntity?.tipo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.animalVacunas.home.createOrEditLabel" data-cy="AnimalVacunasCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.animalVacunas.home.createOrEditLabel">Create or edit a AnimalVacunas</Translate>
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
                  id="animal-vacunas-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.animalVacunas.fecha')}
                id="animal-vacunas-fecha"
                name="fecha"
                data-cy="fecha"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.animalVacunas.nombre')}
                id="animal-vacunas-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.animalVacunas.laboratorio')}
                id="animal-vacunas-laboratorio"
                name="laboratorio"
                data-cy="laboratorio"
                type="text"
              />
              <ValidatedField
                label={translate('agrofincaApp.animalVacunas.dosis')}
                id="animal-vacunas-dosis"
                name="dosis"
                data-cy="dosis"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="animal-vacunas-animal"
                name="animalId"
                data-cy="animal"
                label={translate('agrofincaApp.animalVacunas.animal')}
                type="select"
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
                id="animal-vacunas-tipo"
                name="tipoId"
                data-cy="tipo"
                label={translate('agrofincaApp.animalVacunas.tipo')}
                type="select"
              >
                <option value="" key="0" />
                {parametros
                  ? parametros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/animal-vacunas" replace color="info">
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

export default AnimalVacunasUpdate;
