import './home.scss';

import React from 'react';
import { Row, Col, Alert, Button } from 'reactstrap';

export const Home = () => {

  return (
    <Row>
      <span className="hipster" />
      <div>
              <a href="/scorecard/new">
                <Button> New Scorecard </Button>
              </a>
            </div>
            <div>&nbsp;</div> 
            <div>
              <a href="/hole-data/new">
                <Button> Record Hole Data </Button>
              </a>
            </div>
      
    </Row>
  );
};

export default Home;
