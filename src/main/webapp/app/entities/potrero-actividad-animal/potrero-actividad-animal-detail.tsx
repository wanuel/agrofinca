import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './potrero-actividad-animal.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroActividadAnimalDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const potreroActividadAnimalEntity = useAppSelector(state => state.potreroActividadAnimal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="potreroActividadAnimalDetailsHeading">
          <Translate contentKey="agrofincaApp.potreroActividadAnimal.detail.title">PotreroActividadAnimal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{potreroActividadAnimalEntity.id}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividadAnimal.animalId">Animal Id</Translate>
          </dt>
          <dd>{potreroActividadAnimalEntity.animalId ? potreroActividadAnimalEntity.animalId.pastoreo : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividadAnimal.potreroActividadId">Potrero Actividad Id</Translate>
          </dt>
          <dd>{potreroActividadAnimalEntity.potreroActividadId ? potreroActividadAnimalEntity.potreroActividadId.pastoreo : ''}</dd>
        </dl>
        <Button tag={Link} to="/potrero-actividad-animal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/potrero-actividad-animal/${potreroActividadAnimalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PotreroActividadAnimalDetail;
