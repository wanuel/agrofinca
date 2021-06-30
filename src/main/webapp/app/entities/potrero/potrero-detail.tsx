import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './potrero.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const potreroEntity = useAppSelector(state => state.potrero.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="potreroDetailsHeading">
          <Translate contentKey="agrofincaApp.potrero.detail.title">Potrero</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{potreroEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="agrofincaApp.potrero.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{potreroEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="agrofincaApp.potrero.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{potreroEntity.descripcion}</dd>
          <dt>
            <span id="pasto">
              <Translate contentKey="agrofincaApp.potrero.pasto">Pasto</Translate>
            </span>
          </dt>
          <dd>{potreroEntity.pasto}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="agrofincaApp.potrero.area">Area</Translate>
            </span>
          </dt>
          <dd>{potreroEntity.area}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.potrero.finca">Finca</Translate>
          </dt>
          <dd>{potreroEntity.finca ? potreroEntity.finca.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/potrero" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/potrero/${potreroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PotreroDetail;
