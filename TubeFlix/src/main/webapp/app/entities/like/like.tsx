import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILike } from 'app/shared/model/like.model';
import { getEntities } from './like.reducer';

export const Like = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const likeList = useAppSelector(state => state.like.entities);
  const loading = useAppSelector(state => state.like.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="like-heading" data-cy="LikeHeading">
        <Translate contentKey="groupProjectApp.like.home.title">Likes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="groupProjectApp.like.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/like/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="groupProjectApp.like.home.createLabel">Create new Like</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {likeList && likeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="groupProjectApp.like.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="groupProjectApp.like.videoUser">Video User</Translate>
                </th>
                <th>
                  <Translate contentKey="groupProjectApp.like.video">Video</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {likeList.map((like, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/like/${like.id}`} color="link" size="sm">
                      {like.id}
                    </Button>
                  </td>
                  <td>{like.videoUser ? <Link to={`/video/${like.videoUser.id}`}>{like.videoUser.id}</Link> : ''}</td>
                  <td>{like.video ? <Link to={`/video-user/${like.video.id}`}>{like.video.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/like/${like.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/like/${like.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/like/${like.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="groupProjectApp.like.home.notFound">No Likes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Like;
