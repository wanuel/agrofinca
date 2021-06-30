import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './lote.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LoteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const loteEntity = useAppSelector(state => state.lote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loteDetailsHeading">
          <Translate contentKey="agrofincaApp.lote.detail.title">Lote</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{loteEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="agrofincaApp.lote.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{loteEntity.nombre}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="agrofincaApp.lote.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{loteEntity.tipo}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.lote.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>{loteEntity.fecha ? <TextFormat value={loteEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="numeroAnimales">
              <Translate contentKey="agrofincaApp.lote.numeroAnimales">Numero Animales</Translate>
            </span>
          </dt>
          <dd>{loteEntity.numeroAnimales}</dd>
        </dl>
        <Button tag={Link} to="/lote" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lote/${loteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoteDetail;
