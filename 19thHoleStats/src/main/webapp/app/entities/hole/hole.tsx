import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHole } from 'app/shared/model/hole.model';
import { getEntities } from './hole.reducer';

export const Hole = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const holeList = useAppSelector(state => state.hole.entities);
  const loading = useAppSelector(state => state.hole.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="hole-heading" data-cy="HoleHeading">
        <Translate contentKey="passionProjectApp.hole.home.title">Holes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="passionProjectApp.hole.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hole/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="passionProjectApp.hole.home.createLabel">Create new Hole</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {holeList && holeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="passionProjectApp.hole.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.hole.holeNumber">Hole Number</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.hole.par">Par</Translate>
                </th>
                <th>
                  <Translate contentKey="passionProjectApp.hole.course">Course</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {holeList.map((hole, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/hole/${hole.id}`} color="link" size="sm">
                      {hole.id}
                    </Button>
                  </td>
                  <td>{hole.holeNumber}</td>
                  <td>{hole.par}</td>
                  <td>{hole.course ? <Link to={`/course/${hole.course.id}`}>{hole.course.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/hole/${hole.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hole/${hole.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/hole/${hole.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="passionProjectApp.hole.home.notFound">No Holes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Hole;
