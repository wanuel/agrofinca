import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { getEntities as getPotreroActividads } from 'app/entities/potrero-actividad/potrero-actividad.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potrero-actividad-animal.reducer';
import { IPotreroActividadAnimal } from 'app/shared/model/potrero-actividad-animal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroActividadAnimalUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const animals = useAppSelector(state => state.animal.entities);
  const potreroActividads = useAppSelector(state => state.potreroActividad.entities);
  const potreroActividadAnimalEntity = useAppSelector(state => state.potreroActividadAnimal.entity);
  const loading = useAppSelector(state => state.potreroActividadAnimal.loading);
  const updating = useAppSelector(state => state.potreroActividadAnimal.updating);
  const updateSuccess = useAppSelector(state => state.potreroActividadAnimal.updateSuccess);

  const handleClose = () => {
    props.history.push('/potrero-actividad-animal');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAnimals({}));
    dispatch(getPotreroActividads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...potreroActividadAnimalEntity,
      ...values,
      animalId: animals.find(it => it.id.toString() === values.animalIdId.toString()),
      potreroActividadId: potreroActividads.find(it => it.id.toString() === values.potreroActividadIdId.toString()),
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
          ...potreroActividadAnimalEntity,
          animalIdId: potreroActividadAnimalEntity?.animalId?.id,
          potreroActividadIdId: potreroActividadAnimalEntity?.potreroActividadId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.potreroActividadAnimal.home.createOrEditLabel" data-cy="PotreroActividadAnimalCreateUpdateHeading">
            <Translate contentKey="agrofincaApp.potreroActividadAnimal.home.createOrEditLabel">
              Create or edit a PotreroActividadAnimal
            </Translate>
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
                  id="potrero-actividad-animal-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="potrero-actividad-animal-animalId"
                name="animalIdId"
                data-cy="animalId"
                label={translate('agrofincaApp.potreroActividadAnimal.animalId')}
                type="select"
              >
                <option value="" key="0" />
                {animals
                  ? animals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.pastoreo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="potrero-actividad-animal-potreroActividadId"
                name="potreroActividadIdId"
                data-cy="potreroActividadId"
                label={translate('agrofincaApp.potreroActividadAnimal.potreroActividadId')}
                type="select"
              >
                <option value="" key="0" />
                {potreroActividads
                  ? potreroActividads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.pastoreo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/potrero-actividad-animal" replace color="info">
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

export default PotreroActividadAnimalUpdate;
