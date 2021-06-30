import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './animal-imagen.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnimalImagenDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const animalImagenEntity = useAppSelector(state => state.animalImagen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="animalImagenDetailsHeading">
          <Translate contentKey="agrofincaApp.animalImagen.detail.title">AnimalImagen</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{animalImagenEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="agrofincaApp.animalImagen.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>
            {animalImagenEntity.fecha ? <TextFormat value={animalImagenEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="nota">
              <Translate contentKey="agrofincaApp.animalImagen.nota">Nota</Translate>
            </span>
          </dt>
          <dd>{animalImagenEntity.nota}</dd>
          <dt>
            <span id="imagen">
              <Translate contentKey="agrofincaApp.animalImagen.imagen">Imagen</Translate>
            </span>
          </dt>
          <dd>
            {animalImagenEntity.imagen ? (
              <div>
                {animalImagenEntity.imagenContentType ? (
                  <a onClick={openFile(animalImagenEntity.imagenContentType, animalImagenEntity.imagen)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {animalImagenEntity.imagenContentType}, {byteSize(animalImagenEntity.imagen)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="agrofincaApp.animalImagen.animal">Animal</Translate>
          </dt>
          <dd>{animalImagenEntity.animal ? animalImagenEntity.animal.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/animal-imagen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/animal-imagen/${animalImagenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnimalImagenDetail;
