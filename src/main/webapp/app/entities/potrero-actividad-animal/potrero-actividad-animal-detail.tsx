import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './potrero-actividad-animal.reducer';
import { IPotreroActividadAnimal } from 'app/shared/model/potrero-actividad-animal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPotreroActividadAnimalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotreroActividadAnimalDetail = (props: IPotreroActividadAnimalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { potreroActividadAnimalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="agrofincaApp.potreroActividadAnimal.detail.title">PotreroActividadAnimal</Translate> [
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividadAnimal.animal">Animal Id</Translate>
          </dt>
          <dd>{potreroActividadAnimalEntity.animal ? potreroActividadAnimalEntity.animal.nombre : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividadAnimal.potreroActividad">Potrero Actividad Id</Translate>
          </dt>
          <dd>{potreroActividadAnimalEntity.potreroActividad ? potreroActividadAnimalEntity.potreroActividad.potrero.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/potrero-actividad-animal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ potreroActividadAnimal }: IRootState) => ({
  potreroActividadAnimalEntity: potreroActividadAnimal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotreroActividadAnimalDetail);
