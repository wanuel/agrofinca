import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { getEntities as getPotreroActividads } from 'app/entities/potrero-actividad/potrero-actividad.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potrero-actividad-animal.reducer';
import { IPotreroActividadAnimal } from 'app/shared/model/potrero-actividad-animal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPotreroActividadAnimalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotreroActividadAnimalUpdate = (props: IPotreroActividadAnimalUpdateProps) => {
  const [animal, setAnimalId] = useState('0');
  const [potreroActividad, setPotreroActividadId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { potreroActividadAnimalEntity, animals, potreroActividads, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/potrero-actividad-animal');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getAnimals();
    props.getPotreroActividads();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...potreroActividadAnimalEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agrofincaApp.potreroActividadAnimal.home.createOrEditLabel">
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
            <AvForm model={isNew ? {} : potreroActividadAnimalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="potrero-actividad-animal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="potrero-actividad-animal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="potrero-actividad-animal-animal">
                  <Translate contentKey="agrofincaApp.potreroActividadAnimal.animal">Animal Id</Translate>
                </Label>
                <AvInput id="potrero-actividad-animal-animal" type="select" className="form-control" name="animal.id">
                  <option value="" key="0" />
                  {animals
                    ? animals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nombre}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="potrero-actividad-animal-potreroActividad">
                  <Translate contentKey="agrofincaApp.potreroActividadAnimal.potreroActividad">Potrero Actividad Id</Translate>
                </Label>
                <AvInput
                  id="potrero-actividad-animal-potreroActividad"
                  type="select"
                  className="form-control"
                  name="potreroActividad.id"
                >
                  <option value="" key="0" />
                  {potreroActividads
                    ? potreroActividads.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.potrero.nombre}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/potrero-actividad-animal" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  animals: storeState.animal.entities,
  potreroActividads: storeState.potreroActividad.entities,
  potreroActividadAnimalEntity: storeState.potreroActividadAnimal.entity,
  loading: storeState.potreroActividadAnimal.loading,
  updating: storeState.potreroActividadAnimal.updating,
  updateSuccess: storeState.potreroActividadAnimal.updateSuccess,
});

const mapDispatchToProps = {
  getAnimals,
  getPotreroActividads,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotreroActividadAnimalUpdate);
