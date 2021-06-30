import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './finca.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FincaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fincaEntity = useAppSelector(state => state.finca.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fincaDetailsHeading">
          <Translate contentKey="agrofincaApp.finca.detail.title">Finca</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fincaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="agrofincaApp.finca.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{fincaEntity.nombre}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="agrofincaApp.finca.area">Area</Translate>
            </span>
          </dt>
          <dd>{fincaEntity.area}</dd>
        </dl>
        <Button tag={Link} to="/finca" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/finca/${fincaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FincaDetail;
