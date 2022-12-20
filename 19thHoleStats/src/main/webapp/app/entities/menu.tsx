import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/golfer">
        <Translate contentKey="global.menu.entities.golfer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/club">
        <Translate contentKey="global.menu.entities.club" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/course">
        <Translate contentKey="global.menu.entities.course" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/scorecard">
        <Translate contentKey="global.menu.entities.scorecard" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/round">
        <Translate contentKey="global.menu.entities.round" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hole">
        <Translate contentKey="global.menu.entities.hole" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hole-data">
        <Translate contentKey="global.menu.entities.holeData" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
