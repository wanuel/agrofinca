import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './potrero-actividad.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PotreroActividad = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const potreroActividadList = useAppSelector(state => state.potreroActividad.entities);
  const loading = useAppSelector(state => state.potreroActividad.loading);
  const totalItems = useAppSelector(state => state.potreroActividad.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="potrero-actividad-heading" data-cy="PotreroActividadHeading">
        <Translate contentKey="agrofincaApp.potreroActividad.home.title">Potrero Actividads</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="agrofincaApp.potreroActividad.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agrofincaApp.potreroActividad.home.createLabel">Create new Potrero Actividad</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {potreroActividadList && potreroActividadList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaIngreso')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaIngreso">Fecha Ingreso</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaSalida')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaSalida">Fecha Salida</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cantidadBovinos')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadBovinos">Cantidad Bovinos</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cantidadEquinos')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadEquinos">Cantidad Equinos</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cantidadMulares')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.cantidadMulares">Cantidad Mulares</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaLimpia')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.fechaLimpia">Fecha Limpia</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('diasDescanso')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.diasDescanso">Dias Descanso</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('diasCarga')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.diasCarga">Dias Carga</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ocupado')}>
                  <Translate contentKey="agrofincaApp.potreroActividad.ocupado">Ocupado</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="agrofincaApp.potreroActividad.potrero">Potrero</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {potreroActividadList.map((potreroActividad, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${potreroActividad.id}`} color="link" size="sm">
                      {potreroActividad.id}
                    </Button>
                  </td>
                  <td>
                    {potreroActividad.fechaIngreso ? (
                      <TextFormat type="date" value={potreroActividad.fechaIngreso} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {potreroActividad.fechaSalida ? (
                      <TextFormat type="date" value={potreroActividad.fechaSalida} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{potreroActividad.cantidadBovinos}</td>
                  <td>{potreroActividad.cantidadEquinos}</td>
                  <td>{potreroActividad.cantidadMulares}</td>
                  <td>
                    {potreroActividad.fechaLimpia ? (
                      <TextFormat type="date" value={potreroActividad.fechaLimpia} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{potreroActividad.diasDescanso}</td>
                  <td>{potreroActividad.diasCarga}</td>
                  <td>
                    <Translate contentKey={`agrofincaApp.SINO.${potreroActividad.ocupado}`} />
                  </td>
                  <td>
                    {potreroActividad.potrero ? (
                      <Link to={`potrero/${potreroActividad.potrero.id}`}>{potreroActividad.potrero.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${potreroActividad.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${potreroActividad.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${potreroActividad.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="agrofincaApp.potreroActividad.home.notFound">No Potrero Actividads found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={potreroActividadList && potreroActividadList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default PotreroActividad;
