package org.game.console.dtos;

public class AcquireSquareDTO {

  private String player;
  private int xCoordinate;
  private int yCoordinate;
  
  public String getPlayer() {
    return this.player;
  }
  
  public void setPlayer(String player) {
    this.player = player;
  }
  
  public void setXCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public int getXCoordinate() {
    return this.xCoordinate;
  }

  public void setYCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  public int getYCoordinate() {
    return this.yCoordinate;
  }
}
