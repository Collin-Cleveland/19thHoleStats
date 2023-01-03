import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRound } from 'app/shared/model/round.model';
import { getEntities } from './round.reducer';

export const Round = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const roundList = useAppSelector(state => state.round.entities);
  const loading = useAppSelector(state => state.round.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const getGolferName = (id: number) => {
    if (id === 1) {return 'Collin'}
    if (id === 2) {return 'User'}
  }

  const getGolfCourseName = (id: number) => {
    if (id === 1) {return 'Odessa National'}
    if (id === 2) {return 'Frog Hollow'}
  }

  return (
    <div>
      <h2 id="round-heading" data-cy="RoundHeading">
        <Translate contentKey="passionProjectApp.round.home.title">Rounds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.round.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/round/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.round.home.createLabel">Create new Round</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roundList && roundList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th>
                  <Translate contentKey="passionProjectApp.round.id">ID</Translate>
                </th> */}
                <th>
                  <Translate contentKey="passionProjectApp.round.datePlayed">Date Played</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.round.numOfHolesPlayed">Num Of Holes Played</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.round.scorecard">Scorecard</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.round.course">Course</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.round.golfer">Golfer</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roundList.map((round, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/round/${round.id}`} color="link" size="sm">
                      {round.id}
                    </Button>
                  </td> */}
                  <td>{round.datePlayed ? <TextFormat type="date" value={round.datePlayed} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{round.numOfHolesPlayed}</td>
                  <td>{round.scorecard ? <Link to={`/scorecard/${round.scorecard.id}`}>{round.scorecard.id}</Link> : ''}</td>
                  <td>{round.course ? <Link to={`/course/${round.course.id}`}>{getGolfCourseName(round.course.id)}</Link> : ''}</td>
                  <td>{round.golfer ? <Link to={`/golfer/${round.golfer.id}`}>{getGolferName(round.golfer.id)}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/round/${round.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/round/${round.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/round/${round.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.round.home.notFound">No Rounds found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Round;
