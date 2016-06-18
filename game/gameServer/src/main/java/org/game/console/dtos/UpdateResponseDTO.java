package org.game.console.dtos;

import java.util.List;

import org.game.server.vos.PlayerVO;
import org.game.server.vos.SquareVO;

public class UpdateResponseDTO {
  
  private String playerName;
  private SquareVO square;
  private String colour;
  private String gameStatus;
  private List<PlayerVO> players;
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return this.playerName;
  }

  public void setSquare(SquareVO square) {
    this.square = square;
  }

  public SquareVO getSquare() {
    return this.square;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public String getColour() {
    return this.colour;
  }
  
  public void setGameStatus(String gameStatus) {
    this.gameStatus = gameStatus;
  }

  public String getGameStatus() {
    return this.gameStatus;
  }
  
  public List<PlayerVO> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<PlayerVO> players) {
    this.players = players;
  }
}
