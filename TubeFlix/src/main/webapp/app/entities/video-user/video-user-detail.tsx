import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './video-user.reducer';

export const VideoUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const videoUserEntity = useAppSelector(state => state.videoUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="videoUserDetailsHeading">
          <Translate contentKey="groupProjectApp.videoUser.detail.title">VideoUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{videoUserEntity.id}</dd>
          <dt>
            <span id="userName">
              <Translate contentKey="groupProjectApp.videoUser.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{videoUserEntity.userName}</dd>
          <dt>
            <Translate contentKey="groupProjectApp.videoUser.internalUser">Internal User</Translate>
          </dt>
          <dd>{videoUserEntity.internalUser ? videoUserEntity.internalUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/video-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/video-user/${videoUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VideoUserDetail;
