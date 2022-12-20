import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scorecard.reducer';

export const ScorecardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scorecardEntity = useAppSelector(state => state.scorecard.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scorecardDetailsHeading">
          <Translate contentKey="passionProjectApp.scorecard.detail.title">Scorecard</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.id}</dd>
          <dt>
            <span id="teeColor">
              <Translate contentKey="passionProjectApp.scorecard.teeColor">Tee Color</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.teeColor}</dd>
          <dt>
            <span id="totalScore">
              <Translate contentKey="passionProjectApp.scorecard.totalScore">Total Score</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.totalScore}</dd>
          <dt>
            <span id="totalPutts">
              <Translate contentKey="passionProjectApp.scorecard.totalPutts">Total Putts</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.totalPutts}</dd>
          <dt>
            <span id="fairwaysHit">
              <Translate contentKey="passionProjectApp.scorecard.fairwaysHit">Fairways Hit</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.fairwaysHit}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.scorecard.course">Course</Translate>
          </dt>
          <dd>{scorecardEntity.course ? scorecardEntity.course.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/scorecard" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scorecard/${scorecardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScorecardDetail;
