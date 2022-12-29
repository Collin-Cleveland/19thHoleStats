import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import './scorecard-details.scss';


import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scorecard.reducer';

export const ScorecardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scorecardEntity = useAppSelector(state => state.scorecard.entity);
  return (
    <><Row>
      <Col md="8">
        <h2 data-cy="scorecardDetailsHeading">
          <Translate contentKey="passionProjectApp.scorecard.detail.title">Scorecard</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.id}</dd>
          <dt>
            <span id="teeColor">
              <Translate contentKey="passionProjectApp.scorecard.teeColor">Tee Color</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.teeColor}</dd>
          <dt>
            <span id="totalScore">
              <Translate contentKey="passionProjectApp.scorecard.totalScore">Total Score</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.totalScore}</dd>
          <dt>
            <span id="totalPutts">
              <Translate contentKey="passionProjectApp.scorecard.totalPutts">Total Putts</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.totalPutts}</dd>
          <dt>
            <span id="fairwaysHit">
              <Translate contentKey="passionProjectApp.scorecard.fairwaysHit">Fairways Hit</Translate>
            </span>
          </dt>
          <dd>{scorecardEntity.fairwaysHit}</dd>
          <dt>
            <Translate contentKey="passionProjectApp.scorecard.course">Course</Translate>
          </dt>
          <dd>{scorecardEntity.course ? scorecardEntity.course.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/scorecard" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scorecard/${scorecardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
    <div>&nbsp;</div> 
    <div>
        <article className="front9">
          <div className="hole">
            <span>Front</span>
            <span>1</span>
            <span>2</span>
            <span>3</span>
            <span>4</span>
            <span>5</span>
            <span>6</span>
            <span>7</span>
            <span>8</span>
            <span>9</span>
            <span>Out</span>
          </div>
          <div className="par">
            <span>Par</span>
            <span>4</span>
            <span>5</span>
            <span>4</span>
            <span>4</span>
            <span>3</span>
            <span>4</span>
            <span>5</span>
            <span>4</span>
            <span>4</span>
            <span>36</span>
          </div>
          <div className="score">
            <span>Score</span>
            <span>3</span>
            <span>5</span>
            <span>4</span>
            <span>5</span>
            <span>3</span>
            <span>3</span>
            <span>5</span>
            <span>4</span>
            <span>4</span>
            <span className="sub">36</span>
          </div>
        </article><article className="back9">
          <div className="hole">
            <span>Back</span>
            <span>10</span>
            <span>11</span>
            <span>12</span>
            <span>13</span>
            <span>14</span>
            <span>15</span>
            <span>16</span>
            <span>17</span>
            <span>18</span>
            <span>In</span>
            <span>Total</span>
          </div>
          <div className="par">
            <span>Par</span>
            <span>5</span>
            <span>3</span>
            <span>4</span>
            <span>3</span>
            <span>5</span>
            <span>4</span>
            <span>4</span>
            <span>3</span>
            <span>5</span>
            <span>36</span>
            <span>72</span>
          </div>
          <div className="score">
            <span>Score</span>
            <span>6</span>
            <span>2</span>
            <span>5</span>
            <span>4</span>
            <span>5</span>
            <span>6</span>
            <span>4</span>
            <span>3</span>
            <span>6</span>
            <span className="sub">41</span>
            <span className="total">77</span>
          </div>
        </article>
      </div></>

  );
};

export default ScorecardDetail;
