package org.game.console.dtos;

public class JoinGameDTO {

  private String playerName;
  private String roomName;
  
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

}
