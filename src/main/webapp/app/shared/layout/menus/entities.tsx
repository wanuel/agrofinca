import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/cargue">
      <Translate contentKey="global.menu.entities.cargue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/detalle-cargue">
      <Translate contentKey="global.menu.entities.detalleCargue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/error-cargue">
      <Translate contentKey="global.menu.entities.errorCargue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tipo-parametro">
      <Translate contentKey="global.menu.entities.tipoParametro" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/parametro-dominio">
      <Translate contentKey="global.menu.entities.parametroDominio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero">
      <Translate contentKey="global.menu.entities.potrero" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/finca">
      <Translate contentKey="global.menu.entities.finca" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero-actividad">
      <Translate contentKey="global.menu.entities.potreroActividad" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal">
      <Translate contentKey="global.menu.entities.animal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-pastoreo">
      <Translate contentKey="global.menu.entities.animalPastoreo" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/parametros">
      <Translate contentKey="global.menu.entities.parametros" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-costos">
      <Translate contentKey="global.menu.entities.animalCostos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-evento">
      <Translate contentKey="global.menu.entities.animalEvento" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-peso">
      <Translate contentKey="global.menu.entities.animalPeso" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-imagen">
      <Translate contentKey="global.menu.entities.animalImagen" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-vacunas">
      <Translate contentKey="global.menu.entities.animalVacunas" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/persona">
      <Translate contentKey="global.menu.entities.persona" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/potrero-actividad-animal">
      <Translate contentKey="global.menu.entities.potreroActividadAnimal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lote">
      <Translate contentKey="global.menu.entities.lote" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/animal-lote">
      <Translate contentKey="global.menu.entities.animalLote" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/annimal">
      <Translate contentKey="global.menu.entities.annimal" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
