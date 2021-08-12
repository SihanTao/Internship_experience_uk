package com.google;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final HashMap<String, VideoPlaylist> playlistHashMap;
  private final List<String> playlistNames;
  private Video current_playing_video;
  private boolean is_current_playing_video_paused = false;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.current_playing_video = null;
    this.playlistHashMap = new HashMap<>();
    this.playlistNames = new ArrayList<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  private String formattedVideoInfo(Video video) {
    StringBuilder sb = new StringBuilder();
    sb.append(video.getTitle() + " (" + video.getVideoId() + ") [");

    List<String> tags = video.getTags();
    if (tags.size() == 0) {
      sb.append("]");
      return sb.toString();
    }

    for (int i = 0; i < tags.size(); i++) {
      sb.append(tags.get(i));
      if (i == tags.size() - 1) {
        sb.append("]");
      } else {
        sb.append(" ");
      }
    }
    return sb.toString();
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos = videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    for (Video video : videos) {
      System.out.println("\t" + formattedVideoInfo(video));
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

    System.out.print("Currently playing: " + formattedVideoInfo(current_playing_video));

    if (is_current_playing_video_paused) {
      System.out.println(" - PAUSED");
    } else {
      System.out.println();
    }
  }

  // Part 2 : playlist management
  public void createPlaylist(String playlistName) {
    Set<String> playlistsNameSet = playlistHashMap.keySet();

    if (playlistsNameSet.contains(playlistName.toUpperCase())) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists\n");
      return;
    }

    VideoPlaylist videoPlaylist = new VideoPlaylist(playlistName);
    playlistHashMap.put(playlistName.toUpperCase(), videoPlaylist);
    playlistNames.add(playlistName);
    System.out.println("Successfully created new playlist: " + playlistName);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    Set<String> playlistsNameSet = playlistHashMap.keySet();
    String uppercase_playlistName = playlistName.toUpperCase();
    if (!playlistsNameSet.contains(uppercase_playlistName)) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }

    if (videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
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
    if (playlistNames.isEmpty()) {
      System.out.println("No playlists exist yet");
      return;
    }

    System.out.println("Showing all playlists:");
    playlistNames.sort(null);
    for (String name : playlistNames) {
      System.out.println("\t" + name);
    }
  }

  public void showPlaylist(String playlistName) {
    Set<String> playlistNameSet = playlistHashMap.keySet();
    if (!playlistNameSet.contains(playlistName.toUpperCase())) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
      return;
    }

    System.out.println("Showing playlist: " + playlistName);
    VideoPlaylist videoPlaylist = playlistHashMap.get(playlistName.toUpperCase());
    if (videoPlaylist.getVideoIdList().isEmpty()) {
      System.out.println("\tNo videos here yet");
      return;
    }

    for (String name : videoPlaylist.getVideoIdList()) {
      Video video = videoLibrary.getVideo(name);
      System.out.println("\t" + formattedVideoInfo(video));
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (!playlistHashMap.containsKey(playlistName.toUpperCase())) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }

    if (videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;
    }

    VideoPlaylist videoPlaylist = playlistHashMap.get(playlistName.toUpperCase());
    if (!videoPlaylist.getVideoIdList().contains(videoId)) {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      return;
    }

    videoPlaylist.remove_from_videoIdList(videoId);
    playlistHashMap.put(playlistName.toUpperCase(), videoPlaylist);
    Video video = videoLibrary.getVideo(videoId);
    System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
  }

  public void clearPlaylist(String playlistName) {
    if (!playlistHashMap.containsKey(playlistName.toUpperCase())) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
      return;
    }

    VideoPlaylist videoPlaylist = playlistHashMap.get(playlistName.toUpperCase());
    videoPlaylist.clear_playlist();
    playlistHashMap.put(playlistName.toUpperCase(), videoPlaylist);
    System.out.println("Successfully removed all videos from " + playlistName);
  }

  public void deletePlaylist(String playlistName) {
    if (!playlistHashMap.containsKey(playlistName.toUpperCase())) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
      return;
    }

    playlistNames.remove(playlistName);
    playlistHashMap.remove(playlistName.toUpperCase());
    System.out.println("Deleted playlist: " + playlistName);
  }

  public void searchVideos(String searchTerm) {
    List<Video> results = new ArrayList<>();
    for (Video video : videoLibrary.getVideos()) {
      if (video.getTitle().toUpperCase().contains(searchTerm.toUpperCase())) {
        results.add(video);
      }
    }

    results.sort(Comparator.comparing(Video::getTitle));
    printSearchResult(results, searchTerm);
    playVideoInSearch(results, searchTerm);
  }

  private void printSearchResult(List<Video> results, String searchTerm) {
    if (results.isEmpty()) {
      System.out.println("No search results for " + searchTerm);
      return;
    }

    System.out.println("Here are the results for " + searchTerm + ":");
    for (int i = 0; i < results.size(); i++) {
      System.out.println("\t" + (i + 1) + ") " + formattedVideoInfo(results.get(i)));
    }
    System.out.println(
        "Would you like to play any of the above?" + " If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, " + "we will assume it's a no.");
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> results = new ArrayList<>();

    for (Video video : videoLibrary.getVideos()) {
      List<String> tags = video.getTags();
      for (String tag : tags) {
        if (videoTag.equalsIgnoreCase(tag)) {
          results.add(video);
        }
      }
    }

    results.sort(Comparator.comparing(Video::getTitle));
    printSearchResult(results, videoTag);
    playVideoInSearch(results, videoTag);
  }

  private void playVideoInSearch(List<Video> results, String searchTerm) {
    if (results.isEmpty()) {
      return;
    }

    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    try {
      int input_num = Integer.parseInt(input);
      Video video = results.get(input_num - 1);
      playVideo(video.getVideoId());
    } catch (Exception ignored) {
    }
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
