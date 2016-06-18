package org.game.server.manager;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.game.server.constants.ColourConstants;
import org.game.server.vos.GameStateVO;
import org.game.server.vos.PlayerVO;

public class GameStateManager {

  private static GameStateManager instance = new GameStateManager();

  // No new instances of this class can be created outside this class
  private GameStateManager() {

  }

  public static GameStateManager getInstance() {
    return instance;
  }

  private Map<String, GameStateVO> games = new HashMap<String, GameStateVO>();

  public GameStateVO getGame(String roomName) {
    if (roomName == null) {
      return null;
    }
    return games.get(roomName);
  }

  public boolean addGame(String roomName, GameStateVO gameStateVo) {
    try {
      if (roomName == null || gameStateVo == null || games.containsKey(roomName)) {
        return false;
      }
      games.put(roomName, gameStateVo);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public boolean updateGame(String roomName, GameStateVO gameStateVo) {
    try {
      if (roomName == null || gameStateVo == null || !games.containsKey(roomName)) {
        return false;
      }
      games.put(roomName, gameStateVo);
      return true;
    } catch (Exception ex) {
      return false;
    }

  }

  public boolean deleteGame(String roomName, GameStateVO gameStateVo) {
    try {
      if (roomName == null || gameStateVo == null || !games.containsKey(roomName)) {
        return false;
      }
      games.remove(roomName);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
  
  public List<String> getAvailableColours(String roomName) {
    if(roomName == null || games.get(roomName).getPlayers().size() <= 0) {
      return null;
    }
    List<String> usedColours = new ArrayList<String>();
    for(PlayerVO player : games.get(roomName).getPlayers().values()) {
      usedColours.add(player.getColour());
    }
    List<String> available = new ArrayList<String>();
    available.addAll(ColourConstants.allColours);
    available.removeAll(usedColours);
    return available;
  }
}
