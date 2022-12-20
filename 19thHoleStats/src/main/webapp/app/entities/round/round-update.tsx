import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IScorecard } from 'app/shared/model/scorecard.model';
import { getEntities as getScorecards } from 'app/entities/scorecard/scorecard.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { IGolfer } from 'app/shared/model/golfer.model';
import { getEntities as getGolfers } from 'app/entities/golfer/golfer.reducer';
import { IRound } from 'app/shared/model/round.model';
import { getEntity, updateEntity, createEntity, reset } from './round.reducer';

export const RoundUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scorecards = useAppSelector(state => state.scorecard.entities);
  const courses = useAppSelector(state => state.course.entities);
  const golfers = useAppSelector(state => state.golfer.entities);
  const roundEntity = useAppSelector(state => state.round.entity);
  const loading = useAppSelector(state => state.round.loading);
  const updating = useAppSelector(state => state.round.updating);
  const updateSuccess = useAppSelector(state => state.round.updateSuccess);

  const handleClose = () => {
    navigate('/round');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getScorecards({}));
    dispatch(getCourses({}));
    dispatch(getGolfers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.datePlayed = convertDateTimeToServer(values.datePlayed);

    const entity = {
      ...roundEntity,
      ...values,
      scorecard: scorecards.find(it => it.id.toString() === values.scorecard.toString()),
      course: courses.find(it => it.id.toString() === values.course.toString()),
      golfer: golfers.find(it => it.id.toString() === values.golfer.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          datePlayed: displayDefaultDateTime(),
        }
      : {
          ...roundEntity,
          datePlayed: convertDateTimeFromServer(roundEntity.datePlayed),
          scorecard: roundEntity?.scorecard?.id,
          course: roundEntity?.course?.id,
          golfer: roundEntity?.golfer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="passionProjectApp.round.home.createOrEditLabel" data-cy="RoundCreateUpdateHeading">
            <Translate contentKey="passionProjectApp.round.home.createOrEditLabel">Create or edit a Round</Translate>
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
                  id="round-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('passionProjectApp.round.datePlayed')}
                id="round-datePlayed"
                name="datePlayed"
                data-cy="datePlayed"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('passionProjectApp.round.numOfHolesPlayed')}
                id="round-numOfHolesPlayed"
                name="numOfHolesPlayed"
                data-cy="numOfHolesPlayed"
                type="text"
              />
              <ValidatedField
                id="round-scorecard"
                name="scorecard"
                data-cy="scorecard"
                label={translate('passionProjectApp.round.scorecard')}
                type="select"
              >
                <option value="" key="0" />
                {scorecards
                  ? scorecards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="round-course"
                name="course"
                data-cy="course"
                label={translate('passionProjectApp.round.course')}
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
              <ValidatedField
                id="round-golfer"
                name="golfer"
                data-cy="golfer"
                label={translate('passionProjectApp.round.golfer')}
                type="select"
              >
                <option value="" key="0" />
                {golfers
                  ? golfers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/round" replace color="info">
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

export default RoundUpdate;
