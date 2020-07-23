import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAnimal } from 'app/shared/model/animal.model';
import { getEntities as getAnimals } from 'app/entities/animal/animal.reducer';
import { IPotrero } from 'app/shared/model/potrero.model';
import { getEntities as getPotreros } from 'app/entities/potrero/potrero.reducer';
import { getEntity, updateEntity, createEntity, reset } from './potrero-actividad.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPotreroActividadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotreroActividadUpdate = (props: IPotreroActividadUpdateProps) => {
  const [idsanimal, setIdsanimal] = useState([]);
  const [potreroId, setPotreroId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { potreroActividadEntity, animals, potreros, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/potrero-actividad' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAnimals();
    props.getPotreros();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...potreroActividadEntity,
        ...values,
        animals: mapIdList(values.animals),
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
          <h2 id="agrofincaApp.potreroActividad.home.createOrEditLabel">
            <Translate contentKey="agrofincaApp.potreroActividad.home.createOrEditLabel">Create or edit a PotreroActividad</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : potreroActividadEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="potrero-actividad-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="potrero-actividad-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fechaIngresoLabel" for="potrero-actividad-fechaIngreso">
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaIngreso">Fecha Ingreso</Translate>
                </Label>
                <AvField
                  id="potrero-actividad-fechaIngreso"
                  type="date"
                  className="form-control"
                  name="fechaIngreso"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaSalidaLabel" for="potrero-actividad-fechaSalida">
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaSalida">Fecha Salida</Translate>
                </Label>
                <AvField id="potrero-actividad-fechaSalida" type="date" className="form-control" name="fechaSalida" />
              </AvGroup>
              <AvGroup>
                <Label id="cantidadBovinosLabel" for="potrero-actividad-cantidadBovinos">
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadBovinos">Cantidad Bovinos</Translate>
                </Label>
                <AvField
                  id="potrero-actividad-cantidadBovinos"
                  type="string"
                  className="form-control"
                  name="cantidadBovinos"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cantidadEquinosLabel" for="potrero-actividad-cantidadEquinos">
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadEquinos">Cantidad Equinos</Translate>
                </Label>
                <AvField
                  id="potrero-actividad-cantidadEquinos"
                  type="string"
                  className="form-control"
                  name="cantidadEquinos"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cantidadMularesLabel" for="potrero-actividad-cantidadMulares">
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadMulares">Cantidad Mulares</Translate>
                </Label>
                <AvField
                  id="potrero-actividad-cantidadMulares"
                  type="string"
                  className="form-control"
                  name="cantidadMulares"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaLimpiaLabel" for="potrero-actividad-fechaLimpia">
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaLimpia">Fecha Limpia</Translate>
                </Label>
                <AvField id="potrero-actividad-fechaLimpia" type="date" className="form-control" name="fechaLimpia" />
              </AvGroup>
              <AvGroup>
                <Label id="diasDescansoLabel" for="potrero-actividad-diasDescanso">
                  <Translate contentKey="agrofincaApp.potreroActividad.diasDescanso">Dias Descanso</Translate>
                </Label>
                <AvField id="potrero-actividad-diasDescanso" type="string" className="form-control" name="diasDescanso" />
              </AvGroup>
              <AvGroup>
                <Label id="diasCargaLabel" for="potrero-actividad-diasCarga">
                  <Translate contentKey="agrofincaApp.potreroActividad.diasCarga">Dias Carga</Translate>
                </Label>
                <AvField id="potrero-actividad-diasCarga" type="string" className="form-control" name="diasCarga" />
              </AvGroup>
              <AvGroup>
                <Label id="ocupadoLabel" for="potrero-actividad-ocupado">
                  <Translate contentKey="agrofincaApp.potreroActividad.ocupado">Ocupado</Translate>
                </Label>
                <AvInput
                  id="potrero-actividad-ocupado"
                  type="select"
                  className="form-control"
                  name="ocupado"
                  value={(!isNew && potreroActividadEntity.ocupado) || 'S'}
                >
                  <option value="SI">{translate('agrofincaApp.SINO.SI')}</option>
                  <option value="NO">{translate('agrofincaApp.SINO.NO')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="potrero-actividad-animal">
                  <Translate contentKey="agrofincaApp.potreroActividad.animal">Animal</Translate>
                </Label>
                <AvInput
                  id="potrero-actividad-animal"
                  type="select"
                  multiple
                  className="form-control"
                  name="animals"
                  value={potreroActividadEntity.animals && potreroActividadEntity.animals.map(e => e.id)}
                >
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
                <Label for="potrero-actividad-potrero">
                  <Translate contentKey="agrofincaApp.potreroActividad.potrero">Potrero</Translate>
                </Label>
                <AvInput id="potrero-actividad-potrero" type="select" className="form-control" name="potrero.id">
                  <option value="" key="0" />
                  {potreros
                    ? potreros.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nombre}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/potrero-actividad" replace color="info">
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
  potreros: storeState.potrero.entities,
  potreroActividadEntity: storeState.potreroActividad.entity,
  loading: storeState.potreroActividad.loading,
  updating: storeState.potreroActividad.updating,
  updateSuccess: storeState.potreroActividad.updateSuccess,
});

const mapDispatchToProps = {
  getAnimals,
  getPotreros,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotreroActividadUpdate);
