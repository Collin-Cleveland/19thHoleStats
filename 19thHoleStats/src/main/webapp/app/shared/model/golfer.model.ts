import { IUser } from 'app/shared/model/user.model';
import { IRound } from 'app/shared/model/round.model';

export interface IGolfer {
  id?: number;
  name?: string;
  avgScore?: number | null;
  roundsPlayed?: number | null;
  handicap?: number | null;
  user?: IUser | null;
  rounds?: IRound[] | null;
}

export const defaultValue: Readonly<IGolfer> = {};
