package org.game.console.dtos;

public class CreateGameDTO {

  private String playerName;
  private String roomName;
  private Integer blockingTime;
  private Integer minPlayers;
  private Integer maxPlayers;
  private String boardSize;
  
  public String getPlayerName() {
    return this.playerName;
  }
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
  
  public String getRoomName() {
    return this.roomName;
  }
  
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }
  
  public Integer getBlockingTime() {
    return this.blockingTime;
  }
  
  public void setBlockingTime(Integer blockingTime) {
    this.blockingTime = blockingTime;
  }
  
  public Integer getMinPlayers() {
    return this.minPlayers;
  }
  
  public void setMinPlayers(Integer minPlayers) {
    this.minPlayers = minPlayers;
  }
  
  public Integer getMaxPlayers() {
    return this.maxPlayers;
  }
  
  public void setMaxPlayers(Integer maxPlayers) {
    this.maxPlayers = maxPlayers;
  }
  
  public String getBoardSize() {
    return this.boardSize;
  }
  
  public void setBoardSize(String boardSize) {
    this.boardSize = boardSize;
  }
  
}
