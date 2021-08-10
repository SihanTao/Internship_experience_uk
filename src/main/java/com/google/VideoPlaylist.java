package com.google;

/** A class used to represent a Playlist */
class VideoPlaylist {
  private final String name;
  public VideoPlaylist(String name) {
    this.name = name;
  }

  public String getPlaylistName() {
    return name;
  }
}
