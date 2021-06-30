import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-evento.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalEventoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalEventoEntity = useAppSelector(state => state.animalEvento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalEventoDetailsHeading">
          <Translate contentKey="agrofincaApp.animalEvento.detail.title">AnimalEvento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalEventoEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalEvento.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalEventoEntity.fecha ? <TextFormat value={animalEventoEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalEvento.animal">Animal</Translate>
          </dt>
          <dd>{animalEventoEntity.animal ? animalEventoEntity.animal.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalEvento.evento">Evento</Translate>
          </dt>
          <dd>{animalEventoEntity.evento ? animalEventoEntity.evento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-evento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-evento/${animalEventoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalEventoDetail;
