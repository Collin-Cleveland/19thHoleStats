import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './golfer.reducer';

export const GolferDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const golferEntity = useAppSelector(state => state.golfer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="golferDetailsHeading">
          <Translate contentKey="passionProjectApp.golfer.detail.title">Golfer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{golferEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="passionProjectApp.golfer.name">Name</Translate>
            </span>
          </dt>
          <dd>{golferEntity.name}</dd>
          <dt>
            <span id="avgScore">
              <Translate contentKey="passionProjectApp.golfer.avgScore">Avg Score</Translate>
            </span>
          </dt>
          <dd>{golferEntity.avgScore}</dd>
          <dt>
            <span id="roundsPlayed">
              <Translate contentKey="passionProjectApp.golfer.roundsPlayed">Rounds Played</Translate>
            </span>
          </dt>
          <dd>{golferEntity.roundsPlayed}</dd>
          <dt>
            <span id="handicap">
              <Translate contentKey="passionProjectApp.golfer.handicap">Handicap</Translate>
            </span>
          </dt>
          <dd>{golferEntity.handicap}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.golfer.user">User</Translate>
          </dt>
          <dd>{golferEntity.user ? golferEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/golfer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/golfer/${golferEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GolferDetail;
