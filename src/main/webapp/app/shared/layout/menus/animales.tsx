import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import { faHorse,faHorseHead,faDollarSign,faWeight,faSyringe,faImages,faCalendarAlt } from '@fortawesome/free-solid-svg-icons'

const adminMenuItems = (
  <>
	<MenuItem icon={faHorse} to="/animal">
      <Translate contentKey="global.menu.animal.animal" />
    </MenuItem>
	<MenuItem icon="table" to="/animal-ficha">
      <Translate contentKey="global.menu.animal.animalFicha" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero-actividad-animal">
      <Translate contentKey="global.menu.animal.animalPastoreo" />
    </MenuItem>
    <MenuItem icon={faDollarSign} to="/animal-costos">
      <Translate contentKey="global.menu.animal.animalCostos" />
    </MenuItem>
    <MenuItem icon={faCalendarAlt} to="/animal-evento">
      <Translate contentKey="global.menu.animal.animalEvento" />
    </MenuItem>
    <MenuItem icon={faWeight} to="/animal-peso">
      <Translate contentKey="global.menu.animal.animalPeso" />
    </MenuItem>
    <MenuItem icon={faImages} to="/animal-imagen">
      <Translate contentKey="global.menu.animal.animalImagen" />
    </MenuItem>
    <MenuItem icon={faSyringe} to="/animal-vacunas">
      <Translate contentKey="global.menu.animal.animalVacunas" />
    </MenuItem>
  </>
);

export const AnimalesMenu = props => (
  <NavDropdown
    icon={faHorseHead}
    name={translate('global.menu.animal.main')}
    id="animal-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    {adminMenuItems}
  </NavDropdown>
);
