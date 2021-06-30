import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './error-cargue.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ErrorCargueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const errorCargueEntity = useAppSelector(state => state.errorCargue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="errorCargueDetailsHeading">
          <Translate contentKey="agrofincaApp.errorCargue.detail.title">ErrorCargue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{errorCargueEntity.id}</dd>
          <dt>
            <span id="ercaError">
              <Translate contentKey="agrofincaApp.errorCargue.ercaError">Erca Error</Translate>
            </span>
          </dt>
          <dd>{errorCargueEntity.ercaError}</dd>
        </dl>
        <Button tag={Link} to="/error-cargue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/error-cargue/${errorCargueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ErrorCargueDetail;
