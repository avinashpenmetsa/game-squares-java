package org.game.console.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.game.console.dtos.CreateGameDTO;
import org.game.console.dtos.JoinGameDTO;
import org.game.server.service.GameStateService;
import org.game.server.vos.GameStateVO;
import org.game.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  private GameStateService gameStateService;

  public void setGameStateService(GameStateService gameStateService) {
    this.gameStateService = gameStateService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(HttpServletRequest request, Model model) {

    logger.debug("opening home page");

    model.addAttribute("createGameDto", new CreateGameDTO());
    model.addAttribute("joinGameDto", new JoinGameDTO());

    return "home";

  }

  @RequestMapping(value = "/createGame", method = RequestMethod.POST)
  public String homeCreateGame(@ModelAttribute CreateGameDTO createGameDto, HttpServletResponse response, Model model) {

    GameStateVO gameStateVO = new GameStateVO();
    gameStateVO.setRoomName(createGameDto.getRoomName());
    gameStateVO.setBlockingTime(createGameDto.getBlockingTime());
    gameStateVO.setMinPlayers(createGameDto.getMinPlayers());
    gameStateVO.setMaxPlayers(createGameDto.getMaxPlayers());
    gameStateVO.setBoardSize(GameUtil.SquareFromString(createGameDto.getBoardSize()));
    if (gameStateService.createGame(createGameDto.getPlayerName(), gameStateVO) == null
        || createGameDto.getMinPlayers() > createGameDto.getMaxPlayers()) {
      return "redirect:";
    }

    response.addCookie(new Cookie("pName", createGameDto.getPlayerName()));
    response.addCookie(new Cookie("rName", createGameDto.getRoomName()));

    return "redirect:" + createGameDto.getRoomName();
  }

  @RequestMapping(value = "/joinGame", method = RequestMethod.POST)
  public String homeJoinGame(@ModelAttribute JoinGameDTO joinGameDto, HttpServletResponse response, Model model) {

    if (gameStateService.joinGame(joinGameDto.getPlayerName(), joinGameDto.getRoomName()) == null) {
      return "redirect:";
    }

    response.addCookie(new Cookie("pName", joinGameDto.getPlayerName()));
    response.addCookie(new Cookie("rName", joinGameDto.getRoomName()));

    return "redirect:" + joinGameDto.getRoomName();
  }

}
