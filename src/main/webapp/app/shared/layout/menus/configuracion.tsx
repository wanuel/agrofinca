import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import {faCheckCircle , faCubes } from '@fortawesome/free-solid-svg-icons'

const adminMenuItems = (
  <>
    <MenuItem icon={ faCubes } to="/parametros">
      <Translate contentKey="global.menu.configuracion.parametros" />
    </MenuItem>
  </>
);

export const ConfiguracionMenu = props => (
  <NavDropdown
    icon={ faCheckCircle }
    name={translate('global.menu.configuracion.main')}
    id="configuracion-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    {adminMenuItems}
  </NavDropdown>
);
