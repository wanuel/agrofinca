import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

const adminMenuItems = (
  <>
    <MenuItem icon="tachometer-alt" to="/finca">
      <Translate contentKey="global.menu.entities.finca.main">Finca</Translate>
    </MenuItem>
    <MenuItem icon="tachometer-alt" to="/finca-update">
      <Translate contentKey="global.menu.entities.finca.main">Finca-list</Translate>
    </MenuItem>
    
  </>
);

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */
	adminMenuItems}
  </NavDropdown>
);
