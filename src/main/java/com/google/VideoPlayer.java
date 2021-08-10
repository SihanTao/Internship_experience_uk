package com.google;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VideoPlayer {

  private Video current_playing_video;
  private boolean is_current_playing_video_paused = false;
  private final VideoLibrary videoLibrary;
  private final HashMap<String, VideoPlaylist> playlistHashMap;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.current_playing_video = null;
    this.playlistHashMap = new HashMap<>();
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
    is_current_playing_video_paused = false;

  }

  public void stopVideo() {
    if (current_playing_video != null) {
      System.out.println("Stopping video: " + current_playing_video.getTitle());
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
    current_playing_video = null;
    is_current_playing_video_paused = false;
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
    is_current_playing_video_paused = false;
  }

  public void pauseVideo() {
    if (current_playing_video == null) {
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }

    if (!is_current_playing_video_paused) {
      is_current_playing_video_paused = true;
      System.out.println("Pausing video: " + current_playing_video.getTitle());
    } else {
      System.out.println("Video already paused: " + current_playing_video.getTitle());
    }
  }

  public void continueVideo() {
    if (current_playing_video == null) {
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }

    if (!is_current_playing_video_paused) {
      System.out.println("Cannot continue video: Video is not paused");
    } else {
      System.out.println("Continuing video: " + current_playing_video.getTitle());
      is_current_playing_video_paused = false;
    }
  }

  public void showPlaying() {
    if (current_playing_video == null) {
      System.out.println("No video is currently playing");
      return;
    }

    System.out.print("Currently playing: " + current_playing_video.getTitle()+" ("+
        current_playing_video.getVideoId()+") [");

    List<String> tags = current_playing_video.getTags();

    if (tags.size() == 0) {
      System.out.print("]");
    }

    for (int i = 0; i < tags.size(); i++) {
      System.out.print(tags.get(i));
      if (i == tags.size() - 1) {
        System.out.print("]");
      } else {
        System.out.print(" ");
      }
    }

    if (is_current_playing_video_paused) {
      System.out.print(" - PAUSED\n");
    } else {
      System.out.println();
    }
  }


  // Part 2 : playlist management
  public void createPlaylist(String playlistName) {
    Set<String> playlistsNames = playlistHashMap.keySet();

    if (playlistsNames.contains(playlistName.toUpperCase())) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists\n");
      return;
    }

    VideoPlaylist videoPlaylist = new VideoPlaylist(playlistName);
    playlistHashMap.put(playlistName.toUpperCase(), videoPlaylist);
    System.out.println("Successfully created new playlist: " + playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    Set<String> playlistsNames = playlistHashMap.keySet();
    String uppercase_playlistName = playlistName.toUpperCase();
    if (!playlistsNames.contains(uppercase_playlistName)) {
      System.out.println("Cannot add video to " + playlistName +": Playlist does not exist");
      return;
    }

    if (videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot add video to " + playlistName +": Video does not exist");
      return;
    }

    VideoPlaylist videoPlaylist = playlistHashMap.get(uppercase_playlistName);
    if (videoPlaylist.getVideoIdList().contains(videoId)) {
      System.out.println("Cannot add video to " + playlistName + ": Video already added\n");
      return;
    }

    String video_title = videoLibrary.getVideo(videoId).getTitle();
    videoPlaylist.add_to_videoIdList(videoId);
    playlistHashMap.put(playlistName, videoPlaylist);
    System.out.println("Added video to " + playlistName + ": " + video_title);
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