package org.game.console.dtos;

import java.util.List;

import org.game.server.vos.PlayerVO;
import org.game.server.vos.SquareVO;

public class GameDTO {

  private SquareVO square;
  private List<PlayerVO> players;
  private Integer blockingTime;
  private String creator;
  
  public void setSquare(SquareVO square) {
    this.square = square;
  }

  public SquareVO getSquare() {
    return this.square;
  }
  
  public List<PlayerVO> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<PlayerVO> players) {
    this.players = players;
  }
  
  public Integer getBlockingTime() {
    return this.blockingTime;
  }
  
  public void setBlockingTime(Integer blockingTime) {
    this.blockingTime = blockingTime;
  }
  
  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }
}
