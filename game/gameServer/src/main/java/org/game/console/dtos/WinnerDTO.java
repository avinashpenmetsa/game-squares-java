package org.game.console.dtos;

import java.util.List;

public class WinnerDTO {
  
  private List<String> players;
  
  public void setPlayers(List<String> players) {
    this.players = players;
  }
  
  public List<String> getPlayers() {
    return this.players;
  }
}
