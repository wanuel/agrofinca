import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './animal-vacunas.reducer';
import { IAnimalVacunas } from 'app/shared/model/animal-vacunas.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAnimalVacunasDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnimalVacunasDetail = (props: IAnimalVacunasDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { animalVacunasEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="agrofincaApp.animalVacunas.detail.title">AnimalVacunas</Translate> [<b>{animalVacunasEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalVacunas.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalVacunasEntity.fecha ? <TextFormat value={animalVacunasEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="agrofincaApp.animalVacunas.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.nombre}</dd>
          <dt>
            <span id="laboratorio">
              <Translate contentKey="agrofincaApp.animalVacunas.laboratorio">Laboratorio</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.laboratorio}</dd>
          <dt>
            <span id="dosis">
              <Translate contentKey="agrofincaApp.animalVacunas.dosis">Dosis</Translate>
            </span>
          </dt>
          <dd>{animalVacunasEntity.dosis}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalVacunas.animal">Animal</Translate>
          </dt>
          <dd>{animalVacunasEntity.animal ? animalVacunasEntity.animal.nombre : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalVacunas.tipo">Tipo</Translate>
          </dt>
          <dd>{animalVacunasEntity.tipo ? animalVacunasEntity.tipo.descripcion : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-vacunas" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-vacunas/${animalVacunasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ animalVacunas }: IRootState) => ({
  animalVacunasEntity: animalVacunas.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnimalVacunasDetail);
