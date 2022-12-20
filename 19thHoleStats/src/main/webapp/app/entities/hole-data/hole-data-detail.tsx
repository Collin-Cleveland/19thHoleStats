import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hole-data.reducer';

export const HoleDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const holeDataEntity = useAppSelector(state => state.holeData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="holeDataDetailsHeading">
          <Translate contentKey="passionProjectApp.holeData.detail.title">HoleData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{holeDataEntity.id}</dd>
          <dt>
            <span id="holeScore">
              <Translate contentKey="passionProjectApp.holeData.holeScore">Hole Score</Translate>
            </span>
          </dt>
          <dd>{holeDataEntity.holeScore}</dd>
          <dt>
            <span id="putts">
              <Translate contentKey="passionProjectApp.holeData.putts">Putts</Translate>
            </span>
          </dt>
          <dd>{holeDataEntity.putts}</dd>
          <dt>
            <span id="fairwayHit">
              <Translate contentKey="passionProjectApp.holeData.fairwayHit">Fairway Hit</Translate>
            </span>
          </dt>
          <dd>{holeDataEntity.fairwayHit ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.holeData.hole">Hole</Translate>
          </dt>
          <dd>{holeDataEntity.hole ? holeDataEntity.hole.id : ''}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.holeData.scorecard">Scorecard</Translate>
          </dt>
          <dd>{holeDataEntity.scorecard ? holeDataEntity.scorecard.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hole-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hole-data/${holeDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoleDataDetail;
