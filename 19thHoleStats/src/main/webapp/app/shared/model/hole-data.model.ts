import { IHole } from 'app/shared/model/hole.model';
import { IScorecard } from 'app/shared/model/scorecard.model';

export interface IHoleData {
  id?: number;
  holeScore?: number | null;
  putts?: number | null;
  fairwayHit?: boolean | null;
  hole?: IHole | null;
  scorecard?: IScorecard | null;
}

export const defaultValue: Readonly<IHoleData> = {
  fairwayHit: false,
};
