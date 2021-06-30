import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-costos.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalCostosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalCostosEntity = useAppSelector(state => state.animalCostos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalCostosDetailsHeading">
          <Translate contentKey="agrofincaApp.animalCostos.detail.title">AnimalCostos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalCostosEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalCostos.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalCostosEntity.fecha ? <TextFormat value={animalCostosEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="valor">
              <Translate contentKey="agrofincaApp.animalCostos.valor">Valor</Translate>
            </span>
          </dt>
          <dd>{animalCostosEntity.valor}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalCostos.animal">Animal</Translate>
          </dt>
          <dd>{animalCostosEntity.animal ? animalCostosEntity.animal.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalCostos.evento">Evento</Translate>
          </dt>
          <dd>{animalCostosEntity.evento ? animalCostosEntity.evento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-costos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-costos/${animalCostosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalCostosDetail;
