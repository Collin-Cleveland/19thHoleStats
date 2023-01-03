import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClub } from 'app/shared/model/club.model';
import { getEntities } from './club.reducer';

export const Club = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const clubList = useAppSelector(state => state.club.entities);
  const loading = useAppSelector(state => state.club.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="club-heading" data-cy="ClubHeading">
        <Translate contentKey="passionProjectApp.club.home.title">Clubs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.club.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/club/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.club.home.createLabel">Create new Club</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clubList && clubList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th>
                  <Translate contentKey="passionProjectApp.club.id">ID</Translate>
                </th> */}
                <th>
                  <Translate contentKey="passionProjectApp.club.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.club.state">State</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.club.city">City</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clubList.map((club, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/club/${club.id}`} color="link" size="sm">
                      {club.id}
                    </Button>
                  </td> */}
                  <td>{club.name}</td>
                  <td>{club.state}</td>
                  <td>{club.city}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/club/${club.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/club/${club.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/club/${club.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.club.home.notFound">No Clubs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Club;
