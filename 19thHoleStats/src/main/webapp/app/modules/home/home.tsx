import './home.scss';

import React from 'react';
import { Row, Col, Alert, Button } from 'reactstrap';

export const Home = () => {

  return (
    <Row>
      <div className="container">
      <span className="logo" />
      </div>
      &nbsp;
      <div className="text-center">
        <a href="/scorecard/new">
          <Button className="block"> New Scorecard </Button>
        </a>
      </div>
      <div>
        &nbsp;
      </div> 
      <div className="text-center">
        <a href="/hole-data/new">
          <Button className="block"> Record Hole Data </Button>
        </a>
      </div>
    </Row>
  );
};

export default Home;
