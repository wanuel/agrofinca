import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const adminMenuItems = (
  <>
    <MenuItem icon="tools" to="/parametros">
      <Translate contentKey="global.menu.inventario.herramienta" />
    </MenuItem>
	<MenuItem icon="cogs" to="/parametros">
      <Translate contentKey="global.menu.inventario.maquinaria" />
    </MenuItem>
<MenuItem icon="microscope" to="/parametros">
      <Translate contentKey="global.menu.inventario.equipo" />
    </MenuItem>
  </>
);

export const InventariosMenu = props => (
  <NavDropdown
    icon="tasks"
    name={translate('global.menu.inventario.main')}
    id="inventario-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    {adminMenuItems}
  </NavDropdown>
);
