import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-lote.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalLoteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalLoteEntity = useAppSelector(state => state.animalLote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalLoteDetailsHeading">
          <Translate contentKey="agrofincaApp.animalLote.detail.title">AnimalLote</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalLoteEntity.id}</dd>
          <dt>
            <span id="fechaEntrada">
              <Translate contentKey="agrofincaApp.animalLote.fechaEntrada">Fecha Entrada</Translate>
            </span>
          </dt>
          <dd>
            {animalLoteEntity.fechaEntrada ? (
              <TextFormat value={animalLoteEntity.fechaEntrada} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaSalida">
              <Translate contentKey="agrofincaApp.animalLote.fechaSalida">Fecha Salida</Translate>
            </span>
          </dt>
          <dd>
            {animalLoteEntity.fechaSalida ? (
              <TextFormat value={animalLoteEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalLote.animal">Animal</Translate>
          </dt>
          <dd>{animalLoteEntity.animal ? animalLoteEntity.animal.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalLote.lote">Lote</Translate>
          </dt>
          <dd>{animalLoteEntity.lote ? animalLoteEntity.lote.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-lote" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-lote/${animalLoteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalLoteDetail;
