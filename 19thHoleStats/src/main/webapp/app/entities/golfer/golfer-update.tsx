import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IGolfer } from 'app/shared/model/golfer.model';
import { getEntity, updateEntity, createEntity, reset } from './golfer.reducer';

export const GolferUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const golferEntity = useAppSelector(state => state.golfer.entity);
  const loading = useAppSelector(state => state.golfer.loading);
  const updating = useAppSelector(state => state.golfer.updating);
  const updateSuccess = useAppSelector(state => state.golfer.updateSuccess);

  const handleClose = () => {
    navigate('/golfer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...golferEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...golferEntity,
          user: golferEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="passionProjectApp.golfer.home.createOrEditLabel" data-cy="GolferCreateUpdateHeading">
            <Translate contentKey="passionProjectApp.golfer.home.createOrEditLabel">Create or edit a Golfer</Translate>
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
                  id="golfer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('passionProjectApp.golfer.name')}
                id="golfer-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('passionProjectApp.golfer.avgScore')}
                id="golfer-avgScore"
                name="avgScore"
                data-cy="avgScore"
                type="text"
              />
              <ValidatedField
                label={translate('passionProjectApp.golfer.roundsPlayed')}
                id="golfer-roundsPlayed"
                name="roundsPlayed"
                data-cy="roundsPlayed"
                type="text"
              />
              <ValidatedField
                label={translate('passionProjectApp.golfer.handicap')}
                id="golfer-handicap"
                name="handicap"
                data-cy="handicap"
                type="text"
              />
              <ValidatedField id="golfer-user" name="user" data-cy="user" label={translate('passionProjectApp.golfer.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/golfer" replace color="info">
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

export default GolferUpdate;
