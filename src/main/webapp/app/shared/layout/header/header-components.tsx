import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
	<div {...props} className="brand-icon">
		<img src="content/images/cima.png" alt="Logo" />
	</div>
);

export const Brand = props => (
	<NavbarBrand tag={Link} to="/" className="brand-logo">
		<div>
			<table>
				<tr><td align="left">
					<BrandIcon />
				</td></tr>
				<tr><td align="left">
					<span className="brand-title">
						<Translate contentKey="global.title">Agropecuaria CIMA</Translate>
					</span>
					<span className="navbar-version">{appConfig.VERSION}</span>
				</td></tr>
			</table>
		</div>
	</NavbarBrand>
);

export const Home = props => (
	<NavItem>
		<NavLink tag={Link} to="/" className="d-flex align-items-center">
			<FontAwesomeIcon icon="home" />
			<span>
				<Translate contentKey="global.menu.home">Home</Translate>
			</span>
		</NavLink>
	</NavItem>
);
