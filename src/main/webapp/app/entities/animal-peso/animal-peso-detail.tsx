import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-peso.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalPesoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalPesoEntity = useAppSelector(state => state.animalPeso.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalPesoDetailsHeading">
          <Translate contentKey="agrofincaApp.animalPeso.detail.title">AnimalPeso</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalPesoEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalPeso.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalPesoEntity.fecha ? <TextFormat value={animalPesoEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="peso">
              <Translate contentKey="agrofincaApp.animalPeso.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{animalPesoEntity.peso}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalPeso.animal">Animal</Translate>
          </dt>
          <dd>{animalPesoEntity.animal ? animalPesoEntity.animal.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalPeso.evento">Evento</Translate>
          </dt>
          <dd>{animalPesoEntity.evento ? animalPesoEntity.evento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-peso" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-peso/${animalPesoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalPesoDetail;
