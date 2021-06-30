import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnnimal } from 'app/shared/model/annimal.model';
import { getEntities as getAnnimals } from 'app/entities/annimal/annimal.reducer';
import { ILote } from 'app/shared/model/lote.model';
import { getEntities as getLotes } from 'app/entities/lote/lote.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-lote.reducer';
import { IAnimalLote } from 'app/shared/model/animal-lote.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalLoteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const annimals = useAppSelector(state => state.annimal.entities);
  const lotes = useAppSelector(state => state.lote.entities);
  const animalLoteEntity = useAppSelector(state => state.animalLote.entity);
  const loading = useAppSelector(state => state.animalLote.loading);
  const updating = useAppSelector(state => state.animalLote.updating);
  const updateSuccess = useAppSelector(state => state.animalLote.updateSuccess);

  const handleClose = () => {
    props.history.push('/animal-lote' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAnnimals({}));
    dispatch(getLotes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...animalLoteEntity,
      ...values,
      animal: annimals.find(it => it.id.toString() === values.animalId.toString()),
      lote: lotes.find(it => it.id.toString() === values.loteId.toString()),
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
          ...animalLoteEntity,
          animalId: animalLoteEntity?.animal?.id,
          loteId: animalLoteEntity?.lote?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.animalLote.home.createOrEditLabel" data-cy="AnimalLoteCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.animalLote.home.createOrEditLabel">Create or edit a AnimalLote</Translate>
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
                  id="animal-lote-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.animalLote.fechaEntrada')}
                id="animal-lote-fechaEntrada"
                name="fechaEntrada"
                data-cy="fechaEntrada"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.animalLote.fechaSalida')}
                id="animal-lote-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
              />
              <ValidatedField
                id="animal-lote-animal"
                name="animalId"
                data-cy="animal"
                label={translate('agrofincaApp.animalLote.animal')}
                type="select"
              >
                <option value="" key="0" />
                {annimals
                  ? annimals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="animal-lote-lote"
                name="loteId"
                data-cy="lote"
                label={translate('agrofincaApp.animalLote.lote')}
                type="select"
              >
                <option value="" key="0" />
                {lotes
                  ? lotes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/animal-lote" replace color="info">
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

export default AnimalLoteUpdate;
