import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './cargue.reducer';
import { ICargue } from 'app/shared/model/cargue.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Cargue = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const cargueList = useAppSelector(state => state.cargue.entities);
  const loading = useAppSelector(state => state.cargue.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cargue-heading" data-cy="CargueHeading">
        <Translate contentKey="agrofincaApp.cargue.home.title">Cargues</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.cargue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.cargue.home.createLabel">Create new Cargue</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cargueList && cargueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargNroRegistros">Carg Nro Registros</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargJson">Carg Json</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargEntidad">Carg Entidad</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargNombreArchivo">Carg Nombre Archivo</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargEstado">Carg Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargTipo">Carg Tipo</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargEsReproceso">Carg Es Reproceso</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargHash">Carg Hash</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.usuarioCreacion">Usuario Creacion</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.fechaCreacion">Fecha Creacion</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.usuarioModificacion">Usuario Modificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.fechaModificacion">Fecha Modificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargId">Carg Id</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.cargue.cargId">Carg Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cargueList.map((cargue, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cargue.id}`} color="link" size="sm">
                      {cargue.id}
                    </Button>
                  </td>
                  <td>{cargue.cargNroRegistros}</td>
                  <td>{cargue.cargJson}</td>
                  <td>{cargue.cargEntidad}</td>
                  <td>{cargue.cargNombreArchivo}</td>
                  <td>{cargue.cargEstado}</td>
                  <td>{cargue.cargTipo}</td>
                  <td>{cargue.cargEsReproceso}</td>
                  <td>{cargue.cargHash}</td>
                  <td>{cargue.usuarioCreacion}</td>
                  <td>{cargue.fechaCreacion ? <TextFormat type="date" value={cargue.fechaCreacion} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{cargue.usuarioModificacion}</td>
                  <td>
                    {cargue.fechaModificacion ? <TextFormat type="date" value={cargue.fechaModificacion} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{cargue.cargId ? <Link to={`detalle-cargue/${cargue.cargId.id}`}>{cargue.cargId.id}</Link> : ''}</td>
                  <td>{cargue.cargId ? <Link to={`error-cargue/${cargue.cargId.id}`}>{cargue.cargId.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cargue.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cargue.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cargue.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="agrofincaApp.cargue.home.notFound">No Cargues found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Cargue;
