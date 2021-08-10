package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {

  private final String name;
  private final List<String> videoIdList;

  public VideoPlaylist(String name) {
    this.name = name;
    this.videoIdList = new ArrayList<>();
  }

  public String getPlaylistName() {
    return name;
  }

  /**
   * Add a video to a playlist.
   * @param videoId : the unique id for the video to add
   */
  public void add_to_videoIdList(String videoId) {
    this.videoIdList.add(videoId);
  }

  public List<String> getVideoIdList() {
    return videoIdList;
  }

  public void remove_from_videoIdList(String videoId) {
    this.videoIdList.remove(videoId);
  }

  public void clear_playlist() {
    videoIdList.clear();
  }
}
