import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from '../animal/animal.reducer';
import { IAnimal } from 'app/shared/model/animal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

import { Animal } from '../animal/animal';
import { AnimalDetail } from '../animal/animal-detail';


export interface IAnimalProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const AnimalFicha = (props: IAnimalProps) => {
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

	const { animalList, match, loading, totalItems } = props;
	return (
		<div>
			<h2 id="animal-heading">
				<Translate contentKey="agrofincaApp.animal.ficha.title">Ficha de animal</Translate>
			</h2>
			<div className="table-responsive">
				<Tabs>
					<TabList>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.caracteristicas">Caracteristicas</Translate></Tab>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.vacunas">Vacunas</Translate></Tab>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.pesos">Pesos</Translate></Tab>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.pastoreos">Pastoreos</Translate></Tab>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.eventos">Eventos</Translate></Tab>
						<Tab><Translate contentKey="agrofincaApp.animal.ficha.imagenes">Imagenes</Translate></Tab>
					</TabList>
					<TabPanel>
						 
					</TabPanel>
					<TabPanel>
						<h2>Vacunas</h2>
						<Table responsive>
							<thead>
								<tr>
									<th className="hand" onClick={sort('id')}>
										<Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th className="hand" onClick={sort('fecha')}>
										<Translate contentKey="agrofincaApp.animalVacunas.fecha">Fecha</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th className="hand" onClick={sort('nombre')}>
										<Translate contentKey="agrofincaApp.animalVacunas.nombre">Nombre</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th className="hand" onClick={sort('laboratorio')}>
										<Translate contentKey="agrofincaApp.animalVacunas.laboratorio">Laboratorio</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th className="hand" onClick={sort('dosis')}>
										<Translate contentKey="agrofincaApp.animalVacunas.dosis">Dosis</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th>
										<Translate contentKey="agrofincaApp.animalVacunas.animal">Animal</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th>
										<Translate contentKey="agrofincaApp.animalVacunas.tipo">Tipo</Translate> <FontAwesomeIcon icon="sort" />
									</th>
									<th />
								</tr>
							</thead>
							<tbody>
							</tbody>
						</Table>
					</TabPanel>
					<TabPanel>
						<h2>Pesos</h2>
					</TabPanel>
					<TabPanel>
						<h2>Pastoreos</h2>
					</TabPanel>
					<TabPanel>
						<h2>Eventos</h2>
					</TabPanel>
					<TabPanel>
						<h2>Imagenes</h2>
					</TabPanel>
				</Tabs>
			</div>
		</div>
	);
};

const mapStateToProps = ({ animal }: IRootState) => ({
	animalList: animal.entities,
	loading: animal.loading,
	totalItems: animal.totalItems,
});

const mapDispatchToProps = {
	getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnimalFicha);
