import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="page-content rounded pad-5">
    <div className="fs-6">Collin Cleveland</div>
    <div className="fs-8 fw-lighter">
      <a href="http://github.com/collin-cleveland"> GitHub</a>
    </div>
  </div>
);

export default Footer;
