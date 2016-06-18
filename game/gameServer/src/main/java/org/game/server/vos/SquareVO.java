package org.game.server.vos;

import java.util.Objects;

public class SquareVO {

  private Integer xCoordinate;
  private Integer yCoordinate;

  public void setXCoordinate(Integer xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public Integer getXCoordinate() {
    return this.xCoordinate;
  }

  public void setYCoordinate(Integer yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

  public Integer getYCoordinate() {
    return this.yCoordinate;
  }

  @Override
  public int hashCode() {
    final int prime = 53;
    int result = 1;
    result = prime * result + ((xCoordinate == null) ? 0 : xCoordinate.hashCode());
    result = prime * result + ((yCoordinate == null) ? 0 : yCoordinate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SquareVO)) {
      return false;
    }
    SquareVO rhs = (SquareVO) obj;
    return Objects.equals(this.xCoordinate, rhs.xCoordinate) && Objects.equals(this.yCoordinate, rhs.yCoordinate);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SquareVO [xCoordinate=").append(xCoordinate.toString()).append(", yCoordinate=")
        .append(yCoordinate.toString()).append("]");
    return builder.toString();
  }
}
