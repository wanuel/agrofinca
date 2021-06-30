import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cargue.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CargueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cargueEntity = useAppSelector(state => state.cargue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cargueDetailsHeading">
          <Translate contentKey="agrofincaApp.cargue.detail.title">Cargue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.id}</dd>
          <dt>
            <span id="cargNroRegistros">
              <Translate contentKey="agrofincaApp.cargue.cargNroRegistros">Carg Nro Registros</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargNroRegistros}</dd>
          <dt>
            <span id="cargJson">
              <Translate contentKey="agrofincaApp.cargue.cargJson">Carg Json</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargJson}</dd>
          <dt>
            <span id="cargEntidad">
              <Translate contentKey="agrofincaApp.cargue.cargEntidad">Carg Entidad</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargEntidad}</dd>
          <dt>
            <span id="cargNombreArchivo">
              <Translate contentKey="agrofincaApp.cargue.cargNombreArchivo">Carg Nombre Archivo</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargNombreArchivo}</dd>
          <dt>
            <span id="cargEstado">
              <Translate contentKey="agrofincaApp.cargue.cargEstado">Carg Estado</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargEstado}</dd>
          <dt>
            <span id="cargTipo">
              <Translate contentKey="agrofincaApp.cargue.cargTipo">Carg Tipo</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargTipo}</dd>
          <dt>
            <span id="cargEsReproceso">
              <Translate contentKey="agrofincaApp.cargue.cargEsReproceso">Carg Es Reproceso</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargEsReproceso}</dd>
          <dt>
            <span id="cargHash">
              <Translate contentKey="agrofincaApp.cargue.cargHash">Carg Hash</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.cargHash}</dd>
          <dt>
            <span id="usuarioCreacion">
              <Translate contentKey="agrofincaApp.cargue.usuarioCreacion">Usuario Creacion</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.usuarioCreacion}</dd>
          <dt>
            <span id="fechaCreacion">
              <Translate contentKey="agrofincaApp.cargue.fechaCreacion">Fecha Creacion</Translate>
            </span>
          </dt>
          <dd>
            {cargueEntity.fechaCreacion ? <TextFormat value={cargueEntity.fechaCreacion} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="usuarioModificacion">
              <Translate contentKey="agrofincaApp.cargue.usuarioModificacion">Usuario Modificacion</Translate>
            </span>
          </dt>
          <dd>{cargueEntity.usuarioModificacion}</dd>
          <dt>
            <span id="fechaModificacion">
              <Translate contentKey="agrofincaApp.cargue.fechaModificacion">Fecha Modificacion</Translate>
            </span>
          </dt>
          <dd>
            {cargueEntity.fechaModificacion ? (
              <TextFormat value={cargueEntity.fechaModificacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="agrofincaApp.cargue.cargId">Carg Id</Translate>
          </dt>
          <dd>{cargueEntity.cargId ? cargueEntity.cargId.id : ''}</dd>
          <dt>
            <Translate contentKey="agrofincaApp.cargue.cargId">Carg Id</Translate>
          </dt>
          <dd>{cargueEntity.cargId ? cargueEntity.cargId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cargue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cargue/${cargueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CargueDetail;
