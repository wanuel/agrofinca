import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './detalle-cargue.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DetalleCargueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const detalleCargueEntity = useAppSelector(state => state.detalleCargue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="detalleCargueDetailsHeading">
          <Translate contentKey="agrofincaApp.detalleCargue.detail.title">DetalleCargue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{detalleCargueEntity.id}</dd>
          <dt>
            <span id="decaCup">
              <Translate contentKey="agrofincaApp.detalleCargue.decaCup">Deca Cup</Translate>
            </span>
          </dt>
          <dd>{detalleCargueEntity.decaCup}</dd>
          <dt>
            <span id="decaEstado">
              <Translate contentKey="agrofincaApp.detalleCargue.decaEstado">Deca Estado</Translate>
            </span>
          </dt>
          <dd>{detalleCargueEntity.decaEstado}</dd>
          <dt>
            <span id="decaJSON">
              <Translate contentKey="agrofincaApp.detalleCargue.decaJSON">Deca JSON</Translate>
            </span>
          </dt>
          <dd>{detalleCargueEntity.decaJSON}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.detalleCargue.decaId">Deca Id</Translate>
          </dt>
          <dd>{detalleCargueEntity.decaId ? detalleCargueEntity.decaId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/detalle-cargue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/detalle-cargue/${detalleCargueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DetalleCargueDetail;
