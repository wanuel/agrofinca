import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './persona.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personaEntity = useAppSelector(state => state.persona.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personaDetailsHeading">
          <Translate contentKey="agrofincaApp.persona.detail.title">Persona</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personaEntity.id}</dd>
          <dt>
            <span id="tipoDocumento">
              <Translate contentKey="agrofincaApp.persona.tipoDocumento">Tipo Documento</Translate>
            </span>
          </dt>
          <dd>{personaEntity.tipoDocumento}</dd>
          <dt>
            <span id="numDocuemnto">
              <Translate contentKey="agrofincaApp.persona.numDocuemnto">Num Docuemnto</Translate>
            </span>
          </dt>
          <dd>{personaEntity.numDocuemnto}</dd>
          <dt>
            <span id="primerNombre">
              <Translate contentKey="agrofincaApp.persona.primerNombre">Primer Nombre</Translate>
            </span>
          </dt>
          <dd>{personaEntity.primerNombre}</dd>
          <dt>
            <span id="segundoNombre">
              <Translate contentKey="agrofincaApp.persona.segundoNombre">Segundo Nombre</Translate>
            </span>
          </dt>
          <dd>{personaEntity.segundoNombre}</dd>
          <dt>
            <span id="primerApellido">
              <Translate contentKey="agrofincaApp.persona.primerApellido">Primer Apellido</Translate>
            </span>
          </dt>
          <dd>{personaEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">
              <Translate contentKey="agrofincaApp.persona.segundoApellido">Segundo Apellido</Translate>
            </span>
          </dt>
          <dd>{personaEntity.segundoApellido}</dd>
          <dt>
            <span id="fechaNacimiento">
              <Translate contentKey="agrofincaApp.persona.fechaNacimiento">Fecha Nacimiento</Translate>
            </span>
          </dt>
          <dd>
            {personaEntity.fechaNacimiento ? (
              <TextFormat value={personaEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="genero">
              <Translate contentKey="agrofincaApp.persona.genero">Genero</Translate>
            </span>
          </dt>
          <dd>{personaEntity.genero}</dd>
        </dl>
        <Button tag={Link} to="/persona" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/persona/${personaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonaDetail;
