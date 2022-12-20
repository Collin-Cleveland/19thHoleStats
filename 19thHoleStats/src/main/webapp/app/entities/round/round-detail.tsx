import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './round.reducer';

export const RoundDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const roundEntity = useAppSelector(state => state.round.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roundDetailsHeading">
          <Translate contentKey="passionProjectApp.round.detail.title">Round</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roundEntity.id}</dd>
          <dt>
            <span id="datePlayed">
              <Translate contentKey="passionProjectApp.round.datePlayed">Date Played</Translate>
            </span>
          </dt>
          <dd>{roundEntity.datePlayed ? <TextFormat value={roundEntity.datePlayed} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="numOfHolesPlayed">
              <Translate contentKey="passionProjectApp.round.numOfHolesPlayed">Num Of Holes Played</Translate>
            </span>
          </dt>
          <dd>{roundEntity.numOfHolesPlayed}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.round.scorecard">Scorecard</Translate>
          </dt>
          <dd>{roundEntity.scorecard ? roundEntity.scorecard.id : ''}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.round.course">Course</Translate>
          </dt>
          <dd>{roundEntity.course ? roundEntity.course.id : ''}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.round.golfer">Golfer</Translate>
          </dt>
          <dd>{roundEntity.golfer ? roundEntity.golfer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/round" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/round/${roundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoundDetail;
