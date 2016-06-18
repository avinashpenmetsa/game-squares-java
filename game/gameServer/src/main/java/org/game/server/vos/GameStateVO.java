package org.game.server.vos;

import java.util.Map;
import java.util.Objects;

public class GameStateVO {

  private String roomName;
  private Integer blockingTime;
  private Integer minPlayers;
  private Integer maxPlayers;
  private SquareVO boardSize;
  private String creator;
  private String gameStatus;
  private UpdateVO latestUpdate;
  private Map<String, PlayerVO> players;

  public String getRoomName() {
    return this.roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public Integer getBlockingTime() {
    return this.blockingTime;
  }

  public void setBlockingTime(Integer blockingTime) {
    this.blockingTime = blockingTime;
  }

  public Integer getMinPlayers() {
    return this.minPlayers;
  }

  public void setMinPlayers(Integer minPlayers) {
    this.minPlayers = minPlayers;
  }

  public Integer getMaxPlayers() {
    return this.maxPlayers;
  }

  public void setMaxPlayers(Integer maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public SquareVO getBoardSize() {
    return this.boardSize;
  }

  public void setBoardSize(SquareVO boardSize) {
    this.boardSize = boardSize;
  }

  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getGameStatus() {
    return this.gameStatus;
  }

  public void setGameStatus(String gameStatus) {
    this.gameStatus = gameStatus;
  }

  public UpdateVO getLatestUpdate() {
    return this.latestUpdate;
  }

  public void setLatestUpdate(UpdateVO latestUpdate) {
    this.latestUpdate = latestUpdate;
  }

  public Map<String, PlayerVO> getPlayers() {
    return this.players;
  }

  public void setPlayers(Map<String, PlayerVO> players) {
    this.players = players;
  }

  @Override
  public int hashCode() {
    final int prime = 51;
    int result = 1;
    result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
    result = prime * result + ((blockingTime == null) ? 0 : blockingTime.hashCode());
    result = prime * result + ((minPlayers == null) ? 0 : minPlayers.hashCode());
    result = prime * result + ((maxPlayers == null) ? 0 : maxPlayers.hashCode());
    result = prime * result + ((boardSize == null) ? 0 : boardSize.hashCode());
    result = prime * result + ((creator == null) ? 0 : creator.hashCode());
    result = prime * result + ((gameStatus == null) ? 0 : gameStatus.hashCode());
    result = prime * result + ((latestUpdate == null) ? 0 : latestUpdate.hashCode());
    result = prime * result + ((players == null) ? 0 : players.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GameStateVO)) {
      return false;
    }
    GameStateVO rhs = (GameStateVO) obj;
    return Objects.equals(this.roomName, rhs.roomName) && Objects.equals(this.blockingTime, rhs.blockingTime)
        && Objects.equals(this.minPlayers, rhs.minPlayers) && Objects.equals(this.maxPlayers, rhs.maxPlayers)
        && Objects.equals(this.boardSize, rhs.boardSize) && Objects.equals(this.creator, rhs.creator)
        && Objects.equals(this.gameStatus, rhs.gameStatus) && Objects.equals(this.players, rhs.players)
        && Objects.equals(this.latestUpdate, rhs.latestUpdate);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GameStateVO [roomName=").append(roomName.toString()).append(", blockingTime=")
        .append(blockingTime.toString()).append(", minPlayers=").append(minPlayers).append(", maxPlayers=")
        .append(maxPlayers).append(", boardSize=").append(boardSize).append(", creator=").append(creator)
        .append(", gameStatus=").append(gameStatus).append(", latestUpdate=").append(latestUpdate).append(", players=")
        .append(players).append("]");
    return builder.toString();
  }
}
