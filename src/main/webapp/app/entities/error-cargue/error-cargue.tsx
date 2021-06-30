import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './error-cargue.reducer';
import { IErrorCargue } from 'app/shared/model/error-cargue.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ErrorCargue = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const errorCargueList = useAppSelector(state => state.errorCargue.entities);
  const loading = useAppSelector(state => state.errorCargue.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="error-cargue-heading" data-cy="ErrorCargueHeading">
        <Translate contentKey="agrofincaApp.errorCargue.home.title">Error Cargues</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.errorCargue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.errorCargue.home.createLabel">Create new Error Cargue</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {errorCargueList && errorCargueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="agrofincaApp.errorCargue.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.errorCargue.ercaError">Erca Error</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {errorCargueList.map((errorCargue, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${errorCargue.id}`} color="link" size="sm">
                      {errorCargue.id}
                    </Button>
                  </td>
                  <td>{errorCargue.ercaError}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${errorCargue.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${errorCargue.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${errorCargue.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="agrofincaApp.errorCargue.home.notFound">No Error Cargues found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ErrorCargue;
