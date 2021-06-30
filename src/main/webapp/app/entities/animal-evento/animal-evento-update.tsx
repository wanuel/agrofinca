import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IParametros } from 'app/shared/model/parametros.model';
import { getEntities as getParametros } from 'app/entities/parametros/parametros.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-evento.reducer';
import { IAnimalEvento } from 'app/shared/model/animal-evento.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalEventoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const animals = useAppSelector(state => state.animal.entities);
  const parametros = useAppSelector(state => state.parametros.entities);
  const animalEventoEntity = useAppSelector(state => state.animalEvento.entity);
  const loading = useAppSelector(state => state.animalEvento.loading);
  const updating = useAppSelector(state => state.animalEvento.updating);
  const updateSuccess = useAppSelector(state => state.animalEvento.updateSuccess);

  const handleClose = () => {
    props.history.push('/animal-evento' + props.location.search);
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
      ...animalEventoEntity,
      ...values,
      animal: animals.find(it => it.id.toString() === values.animalId.toString()),
      evento: parametros.find(it => it.id.toString() === values.eventoId.toString()),
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
          ...animalEventoEntity,
          animalId: animalEventoEntity?.animal?.id,
          eventoId: animalEventoEntity?.evento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.animalEvento.home.createOrEditLabel" data-cy="AnimalEventoCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.animalEvento.home.createOrEditLabel">Create or edit a AnimalEvento</Translate>
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
                  id="animal-evento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.animalEvento.fecha')}
                id="animal-evento-fecha"
                name="fecha"
                data-cy="fecha"
                type="date"
              />
              <ValidatedField
                id="animal-evento-animal"
                name="animalId"
                data-cy="animal"
                label={translate('agrofincaApp.animalEvento.animal')}
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
                id="animal-evento-evento"
                name="eventoId"
                data-cy="evento"
                label={translate('agrofincaApp.animalEvento.evento')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/animal-evento" replace color="info">
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

export default AnimalEventoUpdate;
