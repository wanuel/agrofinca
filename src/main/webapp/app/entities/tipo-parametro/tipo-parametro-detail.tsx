import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tipo-parametro.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TipoParametroDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tipoParametroEntity = useAppSelector(state => state.tipoParametro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoParametroDetailsHeading">
          <Translate contentKey="agrofincaApp.tipoParametro.detail.title">TipoParametro</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoParametroEntity.id}</dd>
          <dt>
            <span id="tipaDescripcion">
              <Translate contentKey="agrofincaApp.tipoParametro.tipaDescripcion">Tipa Descripcion</Translate>
            </span>
          </dt>
          <dd>{tipoParametroEntity.tipaDescripcion}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.tipoParametro.tipaId">Tipa Id</Translate>
          </dt>
          <dd>{tipoParametroEntity.tipaId ? tipoParametroEntity.tipaId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tipo-parametro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-parametro/${tipoParametroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoParametroDetail;
