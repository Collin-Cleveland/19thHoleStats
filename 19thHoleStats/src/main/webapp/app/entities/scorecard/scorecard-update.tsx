import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { IRound } from 'app/shared/model/round.model';
import { getEntities as getRounds } from 'app/entities/round/round.reducer';
import { IScorecard } from 'app/shared/model/scorecard.model';
import { TeeColor } from 'app/shared/model/enumerations/tee-color.model';
import { getEntity, updateEntity, createEntity, reset } from './scorecard.reducer';

export const ScorecardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const courses = useAppSelector(state => state.course.entities);
  const rounds = useAppSelector(state => state.round.entities);
  const scorecardEntity = useAppSelector(state => state.scorecard.entity);
  const loading = useAppSelector(state => state.scorecard.loading);
  const updating = useAppSelector(state => state.scorecard.updating);
  const updateSuccess = useAppSelector(state => state.scorecard.updateSuccess);
  const teeColorValues = Object.keys(TeeColor);

  const handleClose = () => {
    navigate('/scorecard');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCourses({}));
    dispatch(getRounds({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...scorecardEntity,
      ...values,
      course: courses.find(it => it.id.toString() === values.course.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          teeColor: 'BLUE',
          ...scorecardEntity,
          course: scorecardEntity?.course?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="passionProjectApp.scorecard.home.createOrEditLabel" data-cy="ScorecardCreateUpdateHeading">
            <Translate contentKey="passionProjectApp.scorecard.home.createOrEditLabel">Create or edit a Scorecard</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="scorecard-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('passionProjectApp.scorecard.teeColor')}
                id="scorecard-teeColor"
                name="teeColor"
                data-cy="teeColor"
                type="select"
              >
                {teeColorValues.map(teeColor => (
                  <option value={teeColor} key={teeColor}>
                    {translate('passionProjectApp.TeeColor.' + teeColor)}
                  </option>
                ))}
              </ValidatedField>
              {/* <ValidatedField
                label={translate('passionProjectApp.scorecard.totalScore')}
                id="scorecard-totalScore"
                name="totalScore"
                data-cy="totalScore"
                type="text"
              />
              <ValidatedField
                label={translate('passionProjectApp.scorecard.totalPutts')}
                id="scorecard-totalPutts"
                name="totalPutts"
                data-cy="totalPutts"
                type="text"
              />
              <ValidatedField
                label={translate('passionProjectApp.scorecard.fairwaysHit')}
                id="scorecard-fairwaysHit"
                name="fairwaysHit"
                data-cy="fairwaysHit"
                type="text"
              /> */}
              <ValidatedField
                id="scorecard-course"
                name="course"
                data-cy="course"
                label={translate('passionProjectApp.scorecard.course')}
                type="select"
              >
                <option value="" key="0" />
                {courses
                  ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/scorecard" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ScorecardUpdate;
