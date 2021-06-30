import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tipo-parametro.reducer';
import { ITipoParametro } from 'app/shared/model/tipo-parametro.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TipoParametro = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tipoParametroList = useAppSelector(state => state.tipoParametro.entities);
  const loading = useAppSelector(state => state.tipoParametro.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tipo-parametro-heading" data-cy="TipoParametroHeading">
        <Translate contentKey="agrofincaApp.tipoParametro.home.title">Tipo Parametros</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.tipoParametro.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.tipoParametro.home.createLabel">Create new Tipo Parametro</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoParametroList && tipoParametroList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="agrofincaApp.tipoParametro.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.tipoParametro.tipaDescripcion">Tipa Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.tipoParametro.tipaId">Tipa Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoParametroList.map((tipoParametro, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tipoParametro.id}`} color="link" size="sm">
                      {tipoParametro.id}
                    </Button>
                  </td>
                  <td>{tipoParametro.tipaDescripcion}</td>
                  <td>
                    {tipoParametro.tipaId ? <Link to={`parametro-dominio/${tipoParametro.tipaId.id}`}>{tipoParametro.tipaId.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tipoParametro.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tipoParametro.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${tipoParametro.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="agrofincaApp.tipoParametro.home.notFound">No Tipo Parametros found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoParametro;
