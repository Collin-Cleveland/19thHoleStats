import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHoleData } from 'app/shared/model/hole-data.model';
import { getEntities } from './hole-data.reducer';

export const HoleData = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const holeDataList = useAppSelector(state => state.holeData.entities);
  const loading = useAppSelector(state => state.holeData.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="hole-data-heading" data-cy="HoleDataHeading">
        <Translate contentKey="passionProjectApp.holeData.home.title">Hole Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.holeData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hole-data/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.holeData.home.createLabel">Create new Hole Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {holeDataList && holeDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.holeScore">Hole Score</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.putts">Putts</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.fairwayHit">Fairway Hit</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.hole">Hole</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.holeData.scorecard">Scorecard</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {holeDataList.map((holeData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/hole-data/${holeData.id}`} color="link" size="sm">
                      {holeData.id}
                    </Button>
                  </td>
                  <td>{holeData.holeScore}</td>
                  <td>{holeData.putts}</td>
                  <td>{holeData.fairwayHit ? 'true' : 'false'}</td>
                  <td>{holeData.hole ? <Link to={`/hole/${holeData.hole.id}`}>{holeData.hole.id}</Link> : ''}</td>
                  <td>{holeData.scorecard ? <Link to={`/scorecard/${holeData.scorecard.id}`}>{holeData.scorecard.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/hole-data/${holeData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hole-data/${holeData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hole-data/${holeData.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.holeData.home.notFound">No Hole Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HoleData;
