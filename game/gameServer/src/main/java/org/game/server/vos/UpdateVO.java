package org.game.server.vos;

import java.util.Objects;

public class UpdateVO {

  private Long time;
  private String playerName;
  private SquareVO square;
  private String colour;

  public void setTime(Long time) {
    this.time = time;
  }

  public Long getTime() {
    return this.time;
  }

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
    result = prime * result + ((square == null) ? 0 : square.hashCode());
    result = prime * result + ((colour == null) ? 0 : colour.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof UpdateVO)) {
      return false;
    }
    UpdateVO rhs = (UpdateVO) obj;
    return Objects.equals(this.playerName, rhs.playerName) && Objects.equals(this.square, rhs.square)
        && Objects.equals(this.colour, rhs.colour);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UpdateVO [playerName=").append(playerName.toString()).append(", square=").append(square.toString())
        .append(", colour=").append(colour).append("]");
    return builder.toString();
  }

}
