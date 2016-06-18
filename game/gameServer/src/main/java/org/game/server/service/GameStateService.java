package org.game.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.game.server.constants.ColourConstants;
import org.game.server.constants.StatusConstants;
import org.game.server.manager.GameStateManager;
import org.game.server.vos.GameStateVO;
import org.game.server.vos.PlayerVO;
import org.game.server.vos.SquareVO;
import org.game.server.vos.UpdateVO;
import org.springframework.beans.factory.annotation.Autowired;

public class GameStateService {

  @Autowired
  private GameStateManager gameStateManager;

  public void setGameStaticManager(GameStateManager gameStaticManager) {
    this.gameStateManager = gameStaticManager;
  }

  public GameStateVO getGame(String roomName, String playerName) {
    try {
      if (roomName == null || playerName == null) {
        return null;
      }
      if (gameStateManager.getGame(roomName).getPlayers().containsKey(playerName)) {
        return gameStateManager.getGame(roomName);
      }
      return null;
    } catch (Exception ex) {
      return null;
    }
  }

  public PlayerVO createGame(String playerName, GameStateVO gameStateVO) {
    if (gameStateVO.getRoomName() == null || gameStateVO.getBlockingTime() == null
        || gameStateVO.getMinPlayers() == null || gameStateVO.getMaxPlayers() == null
        || gameStateVO.getBoardSize() == null || playerName == null) {
      return null;
    }
    // if the roomName already exists do not create new game
    if (gameStateManager.getGame(gameStateVO.getRoomName()) != null) {
      return null;
    }
    int index = (int) (Math.random() * (ColourConstants.allColours.size()));
    String colour = ColourConstants.allColours.get(index);

    PlayerVO playerVO = new PlayerVO();
    playerVO.setPlayerName(playerName);
    playerVO.setColour(colour);

    gameStateVO.setGameStatus(StatusConstants.NOT_STARTED);
    gameStateVO.setPlayers(new HashMap<String, PlayerVO>());
    gameStateVO.getPlayers().put(playerName, playerVO);
    gameStateVO.setCreator(playerName);
    if (gameStateManager.addGame(gameStateVO.getRoomName(), gameStateVO)) {
      return playerVO;
    }
    return null;
  }

  public PlayerVO joinGame(String playerName, String roomName) {
    if (playerName == null || roomName == null || gameStateManager.getGame(roomName) == null) {
      return null;
    }
    GameStateVO gameStateVO = gameStateManager.getGame(roomName);
    // if player already exists in the game room then do not add
    if (gameStateVO.getPlayers().containsKey(playerName)
        || !gameStateVO.getGameStatus().equals(StatusConstants.NOT_STARTED)) {
      return null;
    }
    // if the room is full do not add
    if (gameStateVO.getMaxPlayers() == gameStateVO.getPlayers().size()) {
      return null;
    }

    int index = (int) (Math.random() * (gameStateManager.getAvailableColours(roomName).size() + 1));
    String colour = gameStateManager.getAvailableColours(roomName).get(index);

    PlayerVO playerVO = new PlayerVO();
    playerVO.setPlayerName(playerName);
    playerVO.setColour(colour);

    gameStateVO.getPlayers().put(playerName, playerVO);

    if (gameStateManager.updateGame(roomName, gameStateVO)) {
      return playerVO;
    }
    return null;
  }

  public boolean acquireSquare(String playerName, String roomName, SquareVO square) {
    if (playerName == null || gameStateManager.getGame(roomName) == null || square == null) {
      return false;
    }
    GameStateVO gameStateVO = gameStateManager.getGame(roomName);
    if (!gameStateVO.getPlayers().containsKey(playerName)
        || !gameStateVO.getGameStatus().equals(StatusConstants.IN_PROGRESS) || !validateSquare(gameStateVO, square)) {
      return false;
    }

    PlayerVO playerVO = gameStateVO.getPlayers().get(playerName);
    if (playerVO.getAcquiredSquares() == null) {
      playerVO.setAcquiredSquares(new HashSet<SquareVO>());
    }
    playerVO.getAcquiredSquares().add(square);

    UpdateVO updateVO = new UpdateVO();
    updateVO.setColour(playerVO.getColour());
    updateVO.setPlayerName(playerName);
    updateVO.setSquare(square);
    updateVO.setTime(System.currentTimeMillis());

    gameStateVO.setLatestUpdate(updateVO);
    gameStateVO.getPlayers().put(playerName, playerVO);

    return gameStateManager.updateGame(roomName, gameStateVO);
  }

  public boolean startGame(String playerName, String roomName) {
    if (playerName == null || roomName == null) {
      return false;
    }
    GameStateVO gameStateVO = gameStateManager.getGame(roomName);
    if (!gameStateVO.getCreator().equals(playerName) || !gameStateVO.getGameStatus().equals(StatusConstants.NOT_STARTED)
        || gameStateVO.getPlayers().size() < gameStateVO.getMinPlayers()) {
      return false;
    }

    gameStateVO.setGameStatus(StatusConstants.IN_PROGRESS);
    return gameStateManager.updateGame(roomName, gameStateVO);
  }

  public List<String> isGameOver(String roomName) {
    if (roomName == null || gameStateManager.getGame(roomName) == null) {
      return null;
    }

    GameStateVO gameStateVO = gameStateManager.getGame(roomName);
    Set<SquareVO> all = getAcquiredSquares(gameStateVO);
    if (all.size() == gameStateVO.getBoardSize().getXCoordinate() * gameStateVO.getBoardSize().getYCoordinate()) {
      gameStateVO.setGameStatus(StatusConstants.ENDED);
      gameStateManager.updateGame(roomName, gameStateVO);
      List<String> winners = new ArrayList<String>();
      Integer squares = 0;
      for (PlayerVO player : gameStateVO.getPlayers().values()) {
        if (squares < player.getAcquiredSquares().size()) {
          winners.clear();
          winners.add(player.getPlayerName());
          squares = player.getAcquiredSquares().size();
        } else if (squares == player.getAcquiredSquares().size()) {
          winners.add(player.getPlayerName());
        }
      }
      return winners;
    }
    return null;
  }

  private boolean validateSquare(GameStateVO gameStateVO, SquareVO square) {
    if (gameStateVO.getBoardSize().getXCoordinate() >= square.getXCoordinate() && square.getXCoordinate() >= 0
        && gameStateVO.getBoardSize().getYCoordinate() >= square.getYCoordinate() && square.getYCoordinate() >= 0
        && (gameStateVO.getLatestUpdate() == null || gameStateVO
            .getBlockingTime() <= (System.currentTimeMillis() - gameStateVO.getLatestUpdate().getTime()))) {
      Set<SquareVO> all = getAcquiredSquares(gameStateVO);
      if (all == null || !all.contains(square)) {
        return true;
      }
    }
    return false;
  }

  private Set<SquareVO> getAcquiredSquares(GameStateVO gameStateVO) {
    Set<SquareVO> all = new HashSet<SquareVO>();
    for (PlayerVO player : gameStateVO.getPlayers().values()) {
      if (player.getAcquiredSquares() != null) {
        all.addAll(player.getAcquiredSquares());
      }
    }
    return all;
  }
}
