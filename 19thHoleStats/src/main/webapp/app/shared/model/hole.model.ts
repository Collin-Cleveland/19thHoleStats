import { ICourse } from 'app/shared/model/course.model';
import { IHoleData } from 'app/shared/model/hole-data.model';

export interface IHole {
  id?: number;
  holeNumber?: number | null;
  par?: number | null;
  course?: ICourse | null;
  holeData?: IHoleData | null;
}

export const defaultValue: Readonly<IHole> = {};
