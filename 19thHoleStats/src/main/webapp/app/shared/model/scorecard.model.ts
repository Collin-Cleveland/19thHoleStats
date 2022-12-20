import { ICourse } from 'app/shared/model/course.model';
import { IRound } from 'app/shared/model/round.model';
import { IHoleData } from 'app/shared/model/hole-data.model';
import { TeeColor } from 'app/shared/model/enumerations/tee-color.model';

export interface IScorecard {
  id?: number;
  teeColor?: TeeColor | null;
  totalScore?: number | null;
  totalPutts?: number | null;
  fairwaysHit?: number | null;
  course?: ICourse | null;
  round?: IRound | null;
  holeData?: IHoleData[] | null;
}

export const defaultValue: Readonly<IScorecard> = {};
