import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHole } from 'app/shared/model/hole.model';
import { getEntities as getHoles } from 'app/entities/hole/hole.reducer';
import { IScorecard } from 'app/shared/model/scorecard.model';
import { getEntities as getScorecards } from 'app/entities/scorecard/scorecard.reducer';
import { IHoleData } from 'app/shared/model/hole-data.model';
import { getEntity, updateEntity, createEntity, reset } from './hole-data.reducer';

export const HoleDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const holes = useAppSelector(state => state.hole.entities);
  const scorecards = useAppSelector(state => state.scorecard.entities);
  const holeDataEntity = useAppSelector(state => state.holeData.entity);
  const loading = useAppSelector(state => state.holeData.loading);
  const updating = useAppSelector(state => state.holeData.updating);
  const updateSuccess = useAppSelector(state => state.holeData.updateSuccess);

  const handleClose = () => {
    navigate('/hole-data');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHoles({}));
    dispatch(getScorecards({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...holeDataEntity,
      ...values,
      hole: holes.find(it => it.id.toString() === values.hole.toString()),
      scorecard: scorecards.find(it => it.id.toString() === values.scorecard.toString()),
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
          ...holeDataEntity,
          hole: holeDataEntity?.hole?.id,
          scorecard: holeDataEntity?.scorecard?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="passionProjectApp.holeData.home.createOrEditLabel" data-cy="HoleDataCreateUpdateHeading">
            <Translate contentKey="passionProjectApp.holeData.home.createOrEditLabel">Create or edit a HoleData</Translate>
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
                  id="hole-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('passionProjectApp.holeData.holeScore')}
                id="hole-data-holeScore"
                name="holeScore"
                data-cy="holeScore"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('passionProjectApp.holeData.putts')}
                id="hole-data-putts"
                name="putts"
                data-cy="putts"
                type="text"
              />
              <ValidatedField
                label={translate('passionProjectApp.holeData.fairwayHit')}
                id="hole-data-fairwayHit"
                name="fairwayHit"
                data-cy="fairwayHit"
                check
                type="checkbox"
              />
              <ValidatedField
                id="hole-data-hole"
                name="hole"
                data-cy="hole"
                label={translate('passionProjectApp.holeData.hole')}
                type="select"
              >
                <option value="" key="0" />
                {holes
                  ? holes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="hole-data-scorecard"
                name="scorecard"
                data-cy="scorecard"
                label={translate('passionProjectApp.holeData.scorecard')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hole-data" replace color="info">
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

export default HoleDataUpdate;
