import { IClub } from 'app/shared/model/club.model';
import { IScorecard } from 'app/shared/model/scorecard.model';
import { IHole } from 'app/shared/model/hole.model';
import { IRound } from 'app/shared/model/round.model';

export interface ICourse {
  id?: number;
  name?: string | null;
  par?: number | null;
  club?: IClub | null;
  scorecard?: IScorecard | null;
  holes?: IHole[] | null;
  rounds?: IRound[] | null;
}

export const defaultValue: Readonly<ICourse> = {};
