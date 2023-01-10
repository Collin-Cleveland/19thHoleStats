import React from 'react';
import { Button } from 'reactstrap';

const VideoThumbnail = props => {
  const { videoLink } = props;

  // Extract the video ID from the link
  let videoId = videoLink.split('v=')[1];
  const ampersandIndex = videoId.indexOf('&');
  if (ampersandIndex !== -1) {
    videoId = videoId.substring(0, ampersandIndex);
  }

  // Construct the thumbnail image URL
  const thumbnailUrl = `https://img.youtube.com/vi/${videoId}/maxresdefault.jpg`;

  return <img width="160" src={thumbnailUrl} alt="Video thumbnail" />;
};

export default VideoThumbnail;