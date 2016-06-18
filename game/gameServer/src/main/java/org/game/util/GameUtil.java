package org.game.util;

import org.game.server.vos.SquareVO;

public class GameUtil {

  public static SquareVO SquareFromString(String size) {
    if (size == null) {
      return null;
    }
    SquareVO square = new SquareVO();
    int indexOfX = size.indexOf('x');
    if (size.indexOf('x', indexOfX+1) != -1) {
      return null;
    }
    try {
      square.setXCoordinate(Integer.parseInt(size.substring(0, indexOfX)));
      square.setYCoordinate(Integer.parseInt(size.substring(indexOfX + 1)));
      return square;
    } catch (Exception ex) {
      return null;
    }
  }
  
}
