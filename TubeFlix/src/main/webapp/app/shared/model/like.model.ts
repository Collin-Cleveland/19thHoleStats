import { IVideo } from 'app/shared/model/video.model';
import { IVideoUser } from 'app/shared/model/video-user.model';

export interface ILike {
  id?: number;
  videoUser?: IVideo | null;
  video?: IVideoUser | null;
}

export const defaultValue: Readonly<ILike> = {};
