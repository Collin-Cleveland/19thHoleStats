import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGolfer } from 'app/shared/model/golfer.model';
import { getEntities } from './golfer.reducer';

export const Golfer = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const golferList = useAppSelector(state => state.golfer.entities);
  const loading = useAppSelector(state => state.golfer.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="golfer-heading" data-cy="GolferHeading">
        <Translate contentKey="passionProjectApp.golfer.home.title">Golfers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.golfer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/golfer/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.golfer.home.createLabel">Create new Golfer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {golferList && golferList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.avgScore">Avg Score</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.roundsPlayed">Rounds Played</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.handicap">Handicap</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.golfer.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {golferList.map((golfer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/golfer/${golfer.id}`} color="link" size="sm">
                      {golfer.id}
                    </Button>
                  </td>
                  <td>{golfer.name}</td>
                  <td>{golfer.avgScore}</td>
                  <td>{golfer.roundsPlayed}</td>
                  <td>{golfer.handicap}</td>
                  <td>{golfer.user ? golfer.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/golfer/${golfer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/golfer/${golfer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/golfer/${golfer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.golfer.home.notFound">No Golfers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Golfer;
