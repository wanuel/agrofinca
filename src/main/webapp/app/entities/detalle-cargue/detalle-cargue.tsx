import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './detalle-cargue.reducer';
import { IDetalleCargue } from 'app/shared/model/detalle-cargue.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DetalleCargue = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const detalleCargueList = useAppSelector(state => state.detalleCargue.entities);
  const loading = useAppSelector(state => state.detalleCargue.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="detalle-cargue-heading" data-cy="DetalleCargueHeading">
        <Translate contentKey="agrofincaApp.detalleCargue.home.title">Detalle Cargues</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.detalleCargue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.detalleCargue.home.createLabel">Create new Detalle Cargue</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {detalleCargueList && detalleCargueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="agrofincaApp.detalleCargue.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.detalleCargue.decaCup">Deca Cup</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.detalleCargue.decaEstado">Deca Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.detalleCargue.decaJSON">Deca JSON</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.detalleCargue.decaId">Deca Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {detalleCargueList.map((detalleCargue, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${detalleCargue.id}`} color="link" size="sm">
                      {detalleCargue.id}
                    </Button>
                  </td>
                  <td>{detalleCargue.decaCup}</td>
                  <td>{detalleCargue.decaEstado}</td>
                  <td>{detalleCargue.decaJSON}</td>
                  <td>
                    {detalleCargue.decaId ? <Link to={`error-cargue/${detalleCargue.decaId.id}`}>{detalleCargue.decaId.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${detalleCargue.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${detalleCargue.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${detalleCargue.id}/delete`}
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
              <Translate contentKey="agrofincaApp.detalleCargue.home.notFound">No Detalle Cargues found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DetalleCargue;
