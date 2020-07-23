import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import {faTractor} from '@fortawesome/free-solid-svg-icons'

const adminMenuItems = (
  <>
    <MenuItem icon={faTractor} to="/finca">
      <Translate contentKey="global.menu.finca.finca" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero">
      <Translate contentKey="global.menu.finca.potrero" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero-actividad">
      <Translate contentKey="global.menu.finca.potreroActividad" />
    </MenuItem>
  </>
);

export const FincaMenu = props => (
  <NavDropdown
    icon={faTractor}
    name={translate('global.menu.finca.main')}
    id="finca-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    {adminMenuItems}
  </NavDropdown>
);
