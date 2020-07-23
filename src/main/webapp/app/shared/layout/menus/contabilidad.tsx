import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import {faUserAlt, faPeopleArrows, faFileInvoiceDollar} from '@fortawesome/free-solid-svg-icons'

const adminMenuItems = (
  <>
    <MenuItem icon={faUserAlt} to="/persona">
      <Translate contentKey="global.menu.contabilidad.persona" />
    </MenuItem>
    <MenuItem icon={faPeopleArrows} to="/parametros">
      <Translate contentKey="global.menu.contabilidad.sociedad" />
    </MenuItem>
  </>
);

export const ContabilidadMenu = props => (
  <NavDropdown
    icon={faFileInvoiceDollar}
    name={translate('global.menu.contabilidad.main')}
    id="contabilidad-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    {adminMenuItems}
  </NavDropdown>
);
