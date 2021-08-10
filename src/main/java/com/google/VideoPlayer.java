package com.google;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class VideoPlayer {

  private Video current_playing_video;
  private final VideoLibrary videoLibrary;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.current_playing_video = null;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos = videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    for (Video video : videos) {
      System.out.print("\t"+video.getTitle()+" ("+video.getVideoId()+") [");
      List<String> tags = video.getTags();

      if (tags.size() == 0) {
        System.out.println("]");
      }

      for (int i = 0; i < tags.size(); i++) {
        System.out.print(tags.get(i));
        if (i == tags.size() - 1) {
          System.out.println("]");
        } else {
          System.out.print(" ");
        }
      }
    }
  }

  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }

    if (current_playing_video != null) {
      System.out.println("Stopping video: " + current_playing_video.getTitle());
    }
    System.out.println("Playing video: " + video.getTitle());
    current_playing_video = video;

  }

  public void stopVideo() {
    if (current_playing_video != null) {
      System.out.println("Stopping video: " + current_playing_video.getTitle());
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
    current_playing_video = null;
  }

  public void playRandomVideo() {
    Random random = new Random();
    List<Video> videos = videoLibrary.getVideos();
    int size = videos.size();
    int r = random.nextInt(size);

    Video next_video = videos.get(r);
    if (current_playing_video != null) {
      System.out.println("Stopping video: " + current_playing_video.getTitle());
    }
    System.out.println("Playing video: " + next_video.getTitle());
    current_playing_video = next_video;
  }

  public void pauseVideo() {
    System.out.println("pauseVideo needs implementation");
  }

  public void continueVideo() {
    System.out.println("continueVideo needs implementation");
  }

  public void showPlaying() {
    System.out.println("showPlaying needs implementation");
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}