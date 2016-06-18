package org.game.console.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.game.console.dtos.AcquireSquareDTO;
import org.game.console.dtos.GameDTO;
import org.game.console.dtos.UpdateRequestDTO;
import org.game.console.dtos.UpdateResponseDTO;
import org.game.console.dtos.WinnerDTO;
import org.game.server.constants.StatusConstants;
import org.game.server.service.GameStateService;
import org.game.server.vos.GameStateVO;
import org.game.server.vos.PlayerVO;
import org.game.server.vos.SquareVO;
import org.game.server.vos.UpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

  @Autowired
  private GameStateService gameStateService;

  public void setGameStateService(GameStateService gameStateService) {
    this.gameStateService = gameStateService;
  }

  @RequestMapping(value = "/{roomName}", method = RequestMethod.GET)
  public String gamePage(@PathVariable("roomName") String roomName, HttpServletRequest request, Model model) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }

    GameStateVO gameStateVO = gameStateService.getGame(roomName, playerName);
    model.addAttribute("colour", gameStateVO.getPlayers().get(playerName).getColour());
    model.addAttribute("playerName", playerName);
    model.addAttribute("roomName", roomName);

    return "game";
  }

  @RequestMapping(value = "/play/{roomName}", method = RequestMethod.GET)
  public @ResponseBody GameDTO game(@PathVariable("roomName") String roomName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }

    GameStateVO gameStateVO = gameStateService.getGame(roomName, playerName);
    GameDTO gameDto = new GameDTO();
    gameDto.setBlockingTime(gameStateVO.getBlockingTime());
    gameDto.setPlayers(new ArrayList<PlayerVO>(gameStateVO.getPlayers().values()));
    gameDto.setSquare(gameStateVO.getBoardSize());
    gameDto.setCreator(gameStateVO.getCreator());

    return gameDto;
  }

  @RequestMapping(value = "/play/{roomName}/start", method = RequestMethod.POST)
  public @ResponseBody UpdateResponseDTO startGame(@RequestBody UpdateRequestDTO updateRequestDto,
      @PathVariable String roomName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }
    if (!playerName.equals(updateRequestDto.getPlayer())) {
      return null;
    }

    GameStateVO gameStateVO = gameStateService.getGame(roomName, playerName);
    if (gameStateVO == null) {
      return null;
    }
    if (gameStateService.startGame(playerName, roomName)) {
      UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
      updateResponseDTO.setGameStatus(gameStateService.getGame(roomName, playerName).getGameStatus());
      return updateResponseDTO;
    }
    return null;

  }

  @RequestMapping(value = "/play/{roomName}/acquire", method = RequestMethod.POST)
  public @ResponseBody UpdateResponseDTO acquireSquare(@RequestBody AcquireSquareDTO acquireSquareDto,
      @PathVariable String roomName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }
    if (!playerName.equals(acquireSquareDto.getPlayer())) {
      return null;
    }

    GameStateVO gameStateVO = gameStateService.getGame(roomName, playerName);
    SquareVO square = new SquareVO();
    square.setXCoordinate((acquireSquareDto.getXCoordinate()));
    square.setYCoordinate((acquireSquareDto.getYCoordinate()));
    if (gameStateVO == null || !gameStateService.acquireSquare(playerName, roomName, square)) {
      return null;
    }
    gameStateService.isGameOver(roomName);
    PlayerVO playerVO = gameStateVO.getPlayers().get(playerName);
    UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
    updateResponseDTO.setColour(playerVO.getColour());
    updateResponseDTO.setGameStatus(gameStateService.getGame(roomName, playerName).getGameStatus());
    updateResponseDTO.setPlayerName(playerName);
    updateResponseDTO.setSquare(square);

    return updateResponseDTO;
  }

  @RequestMapping(value = "/play/{roomName}/update", method = RequestMethod.POST)
  public @ResponseBody UpdateResponseDTO updateRequest(@RequestBody UpdateRequestDTO updateRequestDto,
      @PathVariable String roomName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }
    if (!playerName.equals(updateRequestDto.getPlayer())) {
      return null;
    }

    GameStateVO gameStateVO = gameStateService.getGame(roomName, playerName);
    if (gameStateVO == null) {
      return null;
    }
    UpdateVO updateVO = gameStateVO.getLatestUpdate();
    UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
    if (updateVO != null) {
      updateResponseDTO.setColour(updateVO.getColour());
      updateResponseDTO.setPlayerName(updateVO.getPlayerName());
      updateResponseDTO.setSquare(updateVO.getSquare());
    }
    updateResponseDTO.setGameStatus(gameStateVO.getGameStatus());
    if (gameStateVO.getGameStatus().equals(StatusConstants.NOT_STARTED)) {
      updateResponseDTO.setPlayers(new ArrayList<PlayerVO>(gameStateVO.getPlayers().values()));
    }

    return updateResponseDTO;
  }

  @RequestMapping(value = "/play/{roomName}/winners", method = RequestMethod.POST)
  public @ResponseBody WinnerDTO getWinners(@RequestBody UpdateRequestDTO updateRequestDto,
      @PathVariable String roomName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String playerName = null;
    String rName = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("rName")) {
        rName = cookie.getValue();
      }
      if (cookie.getName().equals("pName")) {
        playerName = cookie.getValue();
      }
    }
    if (!rName.equals(roomName)) {
      return null;
    }
    if (!playerName.equals(updateRequestDto.getPlayer())) {
      return null;
    }

    List<String> players = gameStateService.isGameOver(roomName);
    if (players == null) {
      return null;
    }

    WinnerDTO winners = new WinnerDTO();
    winners.setPlayers(players);
    return winners;
  }
}
