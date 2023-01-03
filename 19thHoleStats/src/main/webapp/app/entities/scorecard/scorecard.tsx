import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IScorecard } from 'app/shared/model/scorecard.model';
import { getEntities } from './scorecard.reducer';

export const Scorecard = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const scorecardList = useAppSelector(state => state.scorecard.entities);
  const loading = useAppSelector(state => state.scorecard.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const getGolfCourseName = (id: number) => {
    if (id === 1){return 'Odessa National'}
    if (id === 2){return 'Frog Hollow'}
  }

  // const allHoleData = getAllHoleData();

  // const getTotalRoundScore = (allHoleData: List<HoleDataDTO>) => {
  //   for (var i = 0; i < allHoleData.length; i++) {
  //     var total = total + allHoleData[i];
  //   }
  // }

  return (
    <div>
      <h2 id="scorecard-heading" data-cy="ScorecardHeading">
        <Translate contentKey="passionProjectApp.scorecard.home.title">Scorecards</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.scorecard.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/scorecard/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.scorecard.home.createLabel">Create new Scorecard</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {scorecardList && scorecardList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <div></div>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.scorecard.teeColor">Tee Color</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.scorecard.totalScore">Total Score</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.scorecard.totalPutts">Total Putts</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.scorecard.fairwaysHit">Fairways Hit</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.scorecard.course">Course</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {scorecardList.map((scorecard, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/scorecard/${scorecard.id}`} color="link" size="sm" style={{ textDecoration: 'none' }}>
                      {'View'}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`passionProjectApp.TeeColor.${scorecard.teeColor}`} />
                  </td>
                  <td>{scorecard.totalScore}</td>
                  <td>{scorecard.totalPutts}</td>
                  <td>{scorecard.fairwaysHit}</td>
                  <td>{scorecard.course ? <Link to={`/course/${scorecard.course.id}`} style={{ textDecoration: 'none' }}>
                    {getGolfCourseName(scorecard.course.id)}
                    </Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/scorecard/${scorecard.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/scorecard/${scorecard.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/scorecard/${scorecard.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.scorecard.home.notFound">No Scorecards found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Scorecard;
