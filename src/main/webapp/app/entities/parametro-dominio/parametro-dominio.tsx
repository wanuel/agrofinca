import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './parametro-dominio.reducer';
import { IParametroDominio } from 'app/shared/model/parametro-dominio.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ParametroDominio = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const parametroDominioList = useAppSelector(state => state.parametroDominio.entities);
  const loading = useAppSelector(state => state.parametroDominio.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="parametro-dominio-heading" data-cy="ParametroDominioHeading">
        <Translate contentKey="agrofincaApp.parametroDominio.home.title">Parametro Dominios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.parametroDominio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.parametroDominio.home.createLabel">Create new Parametro Dominio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {parametroDominioList && parametroDominioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="agrofincaApp.parametroDominio.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.parametroDominio.padoId">Pado Id</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.parametroDominio.padoDescripcion">Pado Descripcion</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parametroDominioList.map((parametroDominio, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${parametroDominio.id}`} color="link" size="sm">
                      {parametroDominio.id}
                    </Button>
                  </td>
                  <td>{parametroDominio.padoId}</td>
                  <td>{parametroDominio.padoDescripcion}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${parametroDominio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${parametroDominio.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${parametroDominio.id}/delete`}
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
              <Translate contentKey="agrofincaApp.parametroDominio.home.notFound">No Parametro Dominios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ParametroDominio;
