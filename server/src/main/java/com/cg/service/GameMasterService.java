package com.cg.service;

import com.cg.dto.CocktailDto;
import com.cg.dto.GameStateDto;
import com.cg.dto.GameStateResponse;
import com.cg.exceptions.InternalApplicationException;
import com.cg.external.CocktailsService;
import com.cg.util.SetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class GameMasterService {

    private static final Logger logger = LoggerFactory.getLogger(GameMasterService.class);

    @Value("${env.constants.max_guesses}")
    private int MAX_GUESSES;
    @Value("${env.constants.max_random_cocktail_retries}")
    private int MAX_RANDOM_COCKTAIL_RETRIES;

    private final CocktailsService cocktailsService;
    private final HighScoreService highScoreService;
    private WordService wordService;

    private CocktailDto currentCocktail;
    private int hintsTally;
    private int currentScore;
    private boolean isGameRunning;
    private final Set<String> availableHints;
    private final ArrayList<String> exposedHints;
    private final ArrayList<String> cocktailsEncountered;

    @Autowired
    public GameMasterService(
            CocktailsService cocktailsService,
            HighScoreService highScoreService
    ) {
        this.cocktailsService = cocktailsService;
        this.highScoreService = highScoreService;
        this.wordService = new WordService("");
        this.availableHints = new HashSet<>();
        this.exposedHints = new ArrayList<>();
        this.cocktailsEncountered = new ArrayList<>();
        this.hintsTally = 0;
    }

    public GameStateResponse startGame() {
        logger.info("[+] Starting a new game");
        resetGameState(false);
        isGameRunning = true;

        GameStateDto gameStateDto = buildGameStateDto();
        return new GameStateResponse(200, GameState.GAME_RUNNING.name(), gameStateDto);
    }

    public GameStateResponse progressGame(String guess) {
        if (!isGameRunning) {
            logger.warn("[!] Game not started yet");
            return new GameStateResponse(400, GameState.GAME_NOT_STARTED.name(), null);
        }

        boolean wordGuessed = wordService.evaluateGuess(guess);
        if (!wordGuessed) {
            hintsTally++;
        }

        GameStateDto gameStateDto;
        String gameStatus;

        if (wordGuessed || getGuessesLeft() == 0) {
            gameStateDto = buildGameStateDto();
            gameStatus = wordGuessed ? GameState.GAME_WON.name() : GameState.GAME_LOST.name();
            if (isNewHighScore()) {
                highScoreService.updateHighScore(currentScore);
            }
            if (wordGuessed) {
                resetGameState(true);
            }
        } else {
            wordService.addRandomHints();
            exposeRandomHint();
            gameStatus = GameState.GAME_RUNNING.name();
            gameStateDto = buildGameStateDto();
        }

        return new GameStateResponse(200, gameStatus, gameStateDto);
    }

    public GameStateResponse getGameState() {
        GameStateDto gameStateDto = null;
        String gameStatus = isGameRunning ? GameState.GAME_RUNNING.name() : GameState.GAME_NOT_STARTED.name();

        if (isGameRunning) {
            gameStateDto = buildGameStateDto();
        }

        return new GameStateResponse(200, gameStatus, gameStateDto);
    }

    private void resetGameState(boolean isVictory) {

        if (isVictory) {
            currentScore += getGuessesLeft();
        } else {
            currentScore = 0;
            cocktailsEncountered.clear();
        }

        currentCocktail = findUniqueRandomCocktail();
        wordService = new WordService(currentCocktail.getStrDrink());
        cocktailsEncountered.add(currentCocktail.getStrDrink());

        setupHints();
        exposedHints.clear();
        hintsTally = 0;
    }

    private Integer getGuessesLeft() {
        return MAX_GUESSES - hintsTally;
    }

    private boolean isNewHighScore() {
        return currentScore > highScoreService.getHighScore();
    }

    private void exposeRandomHint() {
        String randomHint = SetUtil.getRandomElementAndPop(availableHints);
        exposedHints.add(randomHint);
    }

    private String[] getExposedHints() {
        return exposedHints.toArray(new String[0]);
    }

    private CocktailDto findUniqueRandomCocktail() {
        int attempts = 0;
        CocktailDto cocktail;
        do {
            cocktail = cocktailsService.getRandomCocktail();
            attempts++;
            if (attempts >= MAX_RANDOM_COCKTAIL_RETRIES) {
                logger.error("[!] Unable to find a new cocktail after {} attempts", MAX_RANDOM_COCKTAIL_RETRIES);
                throw new InternalApplicationException(
                    "Unable to find a new cocktail after "
                    + MAX_RANDOM_COCKTAIL_RETRIES
                    + " attempts"
                );
            }
        } while (cocktailsEncountered.contains(cocktail.getStrDrink()));
        return cocktail;
    }

    private void setupHints() {
        SetUtil.addToSetIfNotNull(availableHints, currentCocktail.getStrGlass());
        SetUtil.addToSetIfNotNull(availableHints, currentCocktail.getIngredientsAsString());
        SetUtil.addToSetIfNotNull(availableHints, currentCocktail.getStrAlcoholic());
        SetUtil.addToSetIfNotNull(availableHints, currentCocktail.getStrCategory());
        availableHints.add(currentCocktail.getStrImageSource() == null ? "NULL" : currentCocktail.getStrImageSource());
    }

    private GameStateDto buildGameStateDto() {
        return GameStateDto.builder()
                .hintedWord(wordService.getHintedWord())
                .hintsLeft(getGuessesLeft())
                .hints(getExposedHints())
                .currentScore(currentScore)
                .hintInstructions(currentCocktail.getStrInstructions())
                .build();
    }

    private enum GameState {
        GAME_NOT_STARTED,
        GAME_RUNNING,
        GAME_WON,
        GAME_LOST
    }
}