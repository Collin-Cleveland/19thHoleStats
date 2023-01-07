import dayjs from 'dayjs';
import { IVideo } from 'app/shared/model/video.model';
import { IVideoUser } from 'app/shared/model/video-user.model';

export interface IComment {
  id?: number;
  commentDate?: string | null;
  body?: string | null;
  video?: IVideo | null;
  videoUser?: IVideoUser | null;
}

export const defaultValue: Readonly<IComment> = {};
