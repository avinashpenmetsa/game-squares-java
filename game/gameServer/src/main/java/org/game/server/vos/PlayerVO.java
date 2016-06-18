package org.game.server.vos;

import java.util.Objects;
import java.util.Set;

public class PlayerVO {

  private String playerName;
  private Set<SquareVO> acquiredSquares;
  private String colour;

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return this.playerName;
  }

  public void setAcquiredSquares(Set<SquareVO> acquiredSquares) {
    this.acquiredSquares = acquiredSquares;
  }

  public Set<SquareVO> getAcquiredSquares() {
    return this.acquiredSquares;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public String getColour() {
    return this.colour;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
    result = prime * result + ((acquiredSquares == null) ? 0 : acquiredSquares.hashCode());
    result = prime * result + ((colour == null) ? 0 : colour.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PlayerVO)) {
      return false;
    }
    PlayerVO rhs = (PlayerVO) obj;
    return Objects.equals(this.playerName, rhs.playerName) && Objects.equals(this.acquiredSquares, rhs.acquiredSquares)
        && Objects.equals(this.colour, rhs.colour);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PlayerVO [playerName=").append(playerName.toString()).append(", acquiredSquares=")
        .append(acquiredSquares.toString()).append(", colour=").append(colour).append("]");
    return builder.toString();
  }

}
