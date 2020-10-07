import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './potrero-actividad.reducer';
import { IPotreroActividad } from 'app/shared/model/potrero-actividad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IPotreroActividadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const PotreroActividad = (props: IPotreroActividadProps) => {
	const [paginationState, setPaginationState] = useState(
		overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
	);

	const getAllEntities = () => {
		props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
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
		const sort = params.get('sort');
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
			order: paginationState.order === 'asc' ? 'desc' : 'asc',
			sort: p,
		});
	};

	const handlePagination = currentPage =>
		setPaginationState({
			...paginationState,
			activePage: currentPage,
		});

	const { potreroActividadList, match, loading, totalItems } = props;
	return (
		<div>
			<h2 id="potrero-actividad-heading">
				<Translate contentKey="agrofincaApp.potreroActividad.home.title">Potrero Actividads</Translate>
				<Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
					<FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="agrofincaApp.potreroActividad.home.createLabel">Create new Potrero Actividad</Translate>
				</Link>
			</h2>
			<div className="table-responsive">
				{potreroActividadList && potreroActividadList.length > 0 ? (
					<Table responsive>
						<thead>
							<tr>
								<th className="hand" onClick={sort('id')}>
									<Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
								</th>
								<th>
									<Translate contentKey="agrofincaApp.potreroActividad.potrero">Potrero</Translate> <FontAwesomeIcon icon="sort" />
								</th>
								<th className="hand" onClick={sort('fechaIngreso')}>
									<Translate contentKey="agrofincaApp.potreroActividad.fechaIngreso">Fecha Ingreso</Translate>{' '}
									<FontAwesomeIcon icon="sort" />
								</th>
								<th className="hand" onClick={sort('fechaSalida')}>
									<Translate contentKey="agrofincaApp.potreroActividad.fechaSalida">Fecha Salida</Translate> <FontAwesomeIcon icon="sort" />
								</th>
								<th className="hand" onClick={sort('fechaLimpia')}>
									<Translate contentKey="agrofincaApp.potreroActividad.fechaLimpia">Fecha Limpia</Translate> <FontAwesomeIcon icon="sort" />
								</th>
								<th className="hand" onClick={sort('cantidadBovinos')}>
									<Translate contentKey="agrofincaApp.potreroActividad.cantidadBovinos">Cantidad de Animales</Translate>{' '}
									<FontAwesomeIcon icon="sort" />
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

								<th />
							</tr>
						</thead>
						<tbody>
							{potreroActividadList.map((potreroActividad, i) => (
								<tr key={`entity-${i}`}>
									<td>
										<Button tag={Link} to={`${match.url}/${potreroActividad.id}`} color="link" size="sm">
											{potreroActividad.id}
										</Button>
									</td>
									<td>
										{potreroActividad.potrero ? (
											<Link to={`potrero/${potreroActividad.potrero.id}`}>{potreroActividad.potrero.nombre}</Link>
										) : (
												''
											)}
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
									<td>
										{potreroActividad.fechaLimpia ? (
											<TextFormat type="date" value={potreroActividad.fechaLimpia} format={APP_LOCAL_DATE_FORMAT} />
										) : null}
									</td>
									<td>{potreroActividad.cantidadBovinos+potreroActividad.cantidadEquinos+potreroActividad.cantidadMulares}={potreroActividad.cantidadBovinos}+{potreroActividad.cantidadEquinos}+{potreroActividad.cantidadMulares}</td>
									
									<td>{potreroActividad.diasDescanso}</td>
									<td>{potreroActividad.diasCarga}</td>
									<td>
										<Translate contentKey={`agrofincaApp.SINO.${potreroActividad.ocupado}`} />
									</td>

									<td className="text-right">
										<div className="btn-group flex-btn-group-container">
											<Button tag={Link} to={`${match.url}/${potreroActividad.id}`} color="info" size="sm">
												<FontAwesomeIcon icon="eye" />{' '}
											</Button>
											<Button
												tag={Link}
												to={`${match.url}/${potreroActividad.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
												color="primary"
												size="sm"
											>
												<FontAwesomeIcon icon="pencil-alt" />{' '}
											</Button>
											<Button
												tag={Link}
												to={`${match.url}/${potreroActividad.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
												color="danger"
												size="sm"
											>
												<FontAwesomeIcon icon="trash" />{' '}
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
			{props.totalItems ? (
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
							totalItems={props.totalItems}
						/>
					</Row>
				</div>
			) : (
					''
				)}
		</div>
	);
};

const mapStateToProps = ({ potreroActividad }: IRootState) => ({
	potreroActividadList: potreroActividad.entities,
	loading: potreroActividad.loading,
	totalItems: potreroActividad.totalItems,
});

const mapDispatchToProps = {
	getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PotreroActividad);
