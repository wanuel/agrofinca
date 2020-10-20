import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getanimales } from 'app/entities/animal/animal.reducer';
import { IParametros } from 'app/shared/model/parametros.model';
import { getEntities as getParametros } from 'app/entities/parametros/parametros.reducer';
import { getEntity, updateEntity, createEntity, reset } from './animal-vacunas.reducer';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnimalVacunasUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnimalVacunasUpdate = (props: IAnimalVacunasUpdateProps) => {
  const [animalId, setAnimalId] = useState('0');
  const [tipoId, setTipoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { animalVacunasEntity, animales, parametros, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/animal-vacunas' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getanimales();
    props.getParametros();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...animalVacunasEntity,
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
          <h2 id="agrofincaApp.animalVacunas.home.createOrEditLabel">
            <Translate contentKey="agrofincaApp.animalVacunas.home.createOrEditLabel">Create or edit a AnimalVacunas</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : animalVacunasEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="animal-vacunas-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="animal-vacunas-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fechaLabel" for="animal-vacunas-fecha">
                  <Translate contentKey="agrofincaApp.animalVacunas.fecha">Fecha</Translate>
                </Label>
                <AvField
                  id="animal-vacunas-fecha"
                  type="date"
                  className="form-control"
                  name="fecha"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="animal-vacunas-nombre">
                  <Translate contentKey="agrofincaApp.animalVacunas.nombre">Nombre</Translate>
                </Label>
                <AvField
                  id="animal-vacunas-nombre"
                  type="text"
                  name="nombre"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="laboratorioLabel" for="animal-vacunas-laboratorio">
                  <Translate contentKey="agrofincaApp.animalVacunas.laboratorio">Laboratorio</Translate>
                </Label>
                <AvField id="animal-vacunas-laboratorio" type="text" name="laboratorio" />
              </AvGroup>
              <AvGroup>
                <Label id="dosisLabel" for="animal-vacunas-dosis">
                  <Translate contentKey="agrofincaApp.animalVacunas.dosis">Dosis</Translate>
                </Label>
                <AvField
                  id="animal-vacunas-dosis"
                  type="text"
                  name="dosis"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="animal-vacunas-animal">
                  <Translate contentKey="agrofincaApp.animalVacunas.animal">Animal</Translate>
                </Label>
                <AvInput id="animal-vacunas-animal" type="select" className="form-control" name="animal.id">
                  <option value="" key="0" />
                  {animales
                    ? animales.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nombre}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="animal-vacunas-tipo">
                  <Translate contentKey="agrofincaApp.animalVacunas.tipo">Tipo</Translate>
                </Label>
                <AvInput id="animal-vacunas-tipo" type="select" className="form-control" name="tipo.id">
                  <option value="" key="0" />
                  {parametros
                    ? parametros.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.descripcion}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/animal-vacunas" replace color="info">
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
  animales: storeState.animal.entities,
  parametros: storeState.parametros.entities,
  animalVacunasEntity: storeState.animalVacunas.entity,
  loading: storeState.animalVacunas.loading,
  updating: storeState.animalVacunas.updating,
  updateSuccess: storeState.animalVacunas.updateSuccess,
});

const mapDispatchToProps = {
  getanimales,
  getParametros,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnimalVacunasUpdate);
