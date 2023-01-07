import dayjs from 'dayjs';
import { IVideoUser } from 'app/shared/model/video-user.model';
import { IComment } from 'app/shared/model/comment.model';

export interface IVideo {
  id?: number;
  videoLink?: string;
  title?: string;
  description?: string | null;
  uploadDate?: string | null;
  uploader?: IVideoUser | null;
  comments?: IComment[] | null;
}

export const defaultValue: Readonly<IVideo> = {};
