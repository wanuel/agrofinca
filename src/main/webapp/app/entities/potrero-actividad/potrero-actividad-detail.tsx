import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './potrero-actividad.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPotreroActividadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PotreroActividadDetail = (props: IPotreroActividadDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { potreroActividadEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="agrofincaApp.potreroActividad.detail.title">PotreroActividad</Translate> [
          <b>{potreroActividadEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fechaIngreso">
              <Translate contentKey="agrofincaApp.potreroActividad.fechaIngreso">Fecha Ingreso</Translate>
            </span>
          </dt>
          <dd>
            {potreroActividadEntity.fechaIngreso ? (
              <TextFormat value={potreroActividadEntity.fechaIngreso} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaSalida">
              <Translate contentKey="agrofincaApp.potreroActividad.fechaSalida">Fecha Salida</Translate>
            </span>
          </dt>
          <dd>
            {potreroActividadEntity.fechaSalida ? (
              <TextFormat value={potreroActividadEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cantidadBovinos">
              <Translate contentKey="agrofincaApp.potreroActividad.cantidadBovinos">Cantidad Bovinos</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.cantidadBovinos}</dd>
          <dt>
            <span id="cantidadEquinos">
              <Translate contentKey="agrofincaApp.potreroActividad.cantidadEquinos">Cantidad Equinos</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.cantidadEquinos}</dd>
          <dt>
            <span id="cantidadMulares">
              <Translate contentKey="agrofincaApp.potreroActividad.cantidadMulares">Cantidad Mulares</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.cantidadMulares}</dd>
          <dt>
            <span id="fechaLimpia">
              <Translate contentKey="agrofincaApp.potreroActividad.fechaLimpia">Fecha Limpia</Translate>
            </span>
          </dt>
          <dd>
            {potreroActividadEntity.fechaLimpia ? (
              <TextFormat value={potreroActividadEntity.fechaLimpia} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="diasDescanso">
              <Translate contentKey="agrofincaApp.potreroActividad.diasDescanso">Dias Descanso</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.diasDescanso}</dd>
          <dt>
            <span id="diasCarga">
              <Translate contentKey="agrofincaApp.potreroActividad.diasCarga">Dias Carga</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.diasCarga}</dd>
          <dt>
            <span id="ocupado">
              <Translate contentKey="agrofincaApp.potreroActividad.ocupado">Ocupado</Translate>
            </span>
          </dt>
          <dd>{potreroActividadEntity.ocupado}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividad.lote">Lote</Translate>
          </dt>
          <dd>
            {potreroActividadEntity.lote ? potreroActividadEntity.lote.nombre : ''}
          </dd>
          <dt>
            <Translate contentKey="agrofincaApp.potreroActividad.potrero">Potrero</Translate>
          </dt>
          <dd>{potreroActividadEntity.potrero ? potreroActividadEntity.potrero.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/potrero-actividad" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/potrero-actividad/${potreroActividadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ potreroActividad }: IRootState) => ({
  potreroActividadEntity: potreroActividad.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotreroActividadDetail);
