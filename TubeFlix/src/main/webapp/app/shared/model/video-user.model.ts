import { IUser } from 'app/shared/model/user.model';
import { IVideo } from 'app/shared/model/video.model';
import { ILike } from 'app/shared/model/like.model';
import { IComment } from 'app/shared/model/comment.model';

export interface IVideoUser {
  id?: number;
  userName?: string;
  internalUser?: IUser | null;
  videos?: IVideo[] | null;
  likes?: ILike[] | null;
  comments?: IComment[] | null;
}

export const defaultValue: Readonly<IVideoUser> = {};
