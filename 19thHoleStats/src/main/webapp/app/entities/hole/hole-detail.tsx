import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

// import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hole.reducer';

export const HoleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const getGolfCourseName = (id: number) => {
    if (id === 1) {return 'Odessa National'}
    if (id === 2) {return 'Frog Hollow'}
  }

  const holeEntity = useAppSelector(state => state.hole.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="holeDetailsHeading">
          <Translate contentKey="passionProjectApp.hole.detail.title">Hole</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{holeEntity.id}</dd>
          <dt>
            <span id="holeNumber">
              <Translate contentKey="passionProjectApp.hole.holeNumber">Hole Number</Translate>
            </span>
          </dt>
          <dd>{holeEntity.holeNumber}</dd>
          <dt>
            <span id="par">
              <Translate contentKey="passionProjectApp.hole.par">Par</Translate>
            </span>
          </dt>
          <dd>{holeEntity.par}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.hole.course">Course</Translate>
          </dt>
          <dd>{holeEntity.course ? getGolfCourseName(holeEntity.course.id) : ''}</dd>
        </dl>
        <Button tag={Link} to="/hole" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hole/${holeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoleDetail;
