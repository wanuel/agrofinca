import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-imagen.reducer';
import { IAnimalImagen } from 'app/shared/model/animal-imagen.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalImagenUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const animals = useAppSelector(state => state.animal.entities);
  const animalImagenEntity = useAppSelector(state => state.animalImagen.entity);
  const loading = useAppSelector(state => state.animalImagen.loading);
  const updating = useAppSelector(state => state.animalImagen.updating);
  const updateSuccess = useAppSelector(state => state.animalImagen.updateSuccess);

  const handleClose = () => {
    props.history.push('/animal-imagen' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAnimals({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...animalImagenEntity,
      ...values,
      animal: animals.find(it => it.id.toString() === values.animalId.toString()),
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
          ...animalImagenEntity,
          animalId: animalImagenEntity?.animal?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.animalImagen.home.createOrEditLabel" data-cy="AnimalImagenCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.animalImagen.home.createOrEditLabel">Create or edit a AnimalImagen</Translate>
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
                  id="animal-imagen-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agrofincaApp.animalImagen.fecha')}
                id="animal-imagen-fecha"
                name="fecha"
                data-cy="fecha"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agrofincaApp.animalImagen.nota')}
                id="animal-imagen-nota"
                name="nota"
                data-cy="nota"
                type="text"
              />
              <ValidatedBlobField
                label={translate('agrofincaApp.animalImagen.imagen')}
                id="animal-imagen-imagen"
                name="imagen"
                data-cy="imagen"
                openActionLabel={translate('entity.action.open')}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="animal-imagen-animal"
                name="animalId"
                data-cy="animal"
                label={translate('agrofincaApp.animalImagen.animal')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/animal-imagen" replace color="info">
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

export default AnimalImagenUpdate;
