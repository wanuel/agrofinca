import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-vacunas.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalVacunasDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalVacunasEntity = useAppSelector(state => state.animalVacunas.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalVacunasDetailsHeading">
          <Translate contentKey="agrofincaApp.animalVacunas.detail.title">AnimalVacunas</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalVacunas.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalVacunasEntity.fecha ? <TextFormat value={animalVacunasEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="agrofincaApp.animalVacunas.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.nombre}</dd>
          <dt>
            <span id="laboratorio">
              <Translate contentKey="agrofincaApp.animalVacunas.laboratorio">Laboratorio</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.laboratorio}</dd>
          <dt>
            <span id="dosis">
              <Translate contentKey="agrofincaApp.animalVacunas.dosis">Dosis</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.dosis}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalVacunas.animal">Animal</Translate>
          </dt>
          <dd>{animalVacunasEntity.animal ? animalVacunasEntity.animal.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalVacunas.tipo">Tipo</Translate>
          </dt>
          <dd>{animalVacunasEntity.tipo ? animalVacunasEntity.tipo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-vacunas" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-vacunas/${animalVacunasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalVacunasDetail;
