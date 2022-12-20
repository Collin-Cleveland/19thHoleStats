import { ICourse } from 'app/shared/model/course.model';

export interface IClub {
  id?: number;
  name?: string | null;
  state?: string | null;
  city?: string | null;
  courses?: ICourse[] | null;
}

export const defaultValue: Readonly<IClub> = {};
