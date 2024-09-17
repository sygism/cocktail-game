package com.ridango.controller;

import com.ridango.dto.GameStateResponse;
import com.ridango.service.GameMasterService;
import com.ridango.service.HighScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameMasterService gameMasterService;
    private final HighScoreService highScoreService;

    public GameController(GameMasterService gameMasterService, HighScoreService highScoreService) {
        this.gameMasterService = gameMasterService;
        this.highScoreService = highScoreService;
    }

    @PostMapping("/start-game")
    public GameStateResponse startGame() {
        return gameMasterService.startGame();
    }

    @GetMapping("/game-state")
    public GameStateResponse getGameState() {
        return gameMasterService.getGameState();
    }

    @GetMapping("/guess")
    public GameStateResponse getNextRound(@RequestParam(value = "guess") String guess) {
        return gameMasterService.progressGame(guess);
    }

    @GetMapping("/high-score")
    public int getHighScore() {
        return highScoreService.getHighScore();
    }
}
