import dayjs from 'dayjs';
import { IScorecard } from 'app/shared/model/scorecard.model';
import { ICourse } from 'app/shared/model/course.model';
import { IGolfer } from 'app/shared/model/golfer.model';

export interface IRound {
  id?: number;
  datePlayed?: string | null;
  numOfHolesPlayed?: number | null;
  scorecard?: IScorecard | null;
  course?: ICourse | null;
  golfer?: IGolfer | null;
}

export const defaultValue: Readonly<IRound> = {};
