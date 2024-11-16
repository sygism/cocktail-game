package com.cg.service;

import com.cg.dto.CocktailDto;
import com.cg.dto.GameStateResponse;
import com.cg.external.CocktailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameMasterServiceTest {

    @Mock
    private CocktailsService cocktailsService;
    @Mock
    private HighScoreService highScoreService;

    private GameMasterService gameMasterService;

    private final String TEST_COCKTAIL = "Test Cocktail";
    private final String WRONG_COCKTAIL = "WRONG COCKTAIL";

    @Before
    public void setup() {
        gameMasterService = new GameMasterService(
            cocktailsService,
            highScoreService
        );

        ReflectionTestUtils.setField(gameMasterService, "MAX_GUESSES", 5);
        ReflectionTestUtils.setField(gameMasterService, "MAX_RANDOM_COCKTAIL_RETRIES", 100);
        when(cocktailsService.getRandomCocktail()).thenReturn(mockCocktail(TEST_COCKTAIL));
        when(highScoreService.getHighScore()).thenReturn(Integer.MAX_VALUE);
    }

    @Test
    public void startGame_ReturnsCorrectGameStateResponse_IfGameStarted() {
        GameStateResponse response = gameMasterService.startGame();

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_RUNNING", response.getGameStatus());
        assertNotNull(response.getGameState());
    }

    @Test
    public void progressGame_ReturnsCorrectGameStateResponse_IfGameNotStarted() {
        GameStateResponse response = gameMasterService.progressGame("Test");

        assertEquals(400, (int) response.getCode());
        assertEquals("GAME_NOT_STARTED", response.getGameStatus());
        assertNull(response.getGameState());
    }

    @Test
    public void progressGame_ReturnsCorrectGameStateResponse_IfUserGuessedWord() {
        gameMasterService.startGame();
        when(cocktailsService.getRandomCocktail()).thenReturn(mockCocktail("ANOTHER_COCKTAIL"));
        GameStateResponse response = gameMasterService.progressGame(TEST_COCKTAIL);

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_WON", response.getGameStatus());
    }

    @Test
    public void progressGame_ReturnsCorrectGameStateResponse_IfUserGuessIncorrect() {
        gameMasterService.startGame();
        GameStateResponse response = gameMasterService.progressGame(WRONG_COCKTAIL);

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_RUNNING", response.getGameStatus());
        assertEquals(1, response.getGameState().getHints().length);
    }

    @Test
    public void progressGame_ReturnsCorrectGameStateResponse_IfUserOutOfHints() {
        gameMasterService.startGame();

        GameStateResponse response = null;

        for (int i = 0; i < 5; i++) {
            response = gameMasterService.progressGame(WRONG_COCKTAIL);
        }

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_LOST", response.getGameStatus());
        assertEquals(4, response.getGameState().getHints().length);
    }

    @Test
    public void progressGame_IncrementsScoreCorrectly_IfUserGuessedWord() {
        gameMasterService.startGame();
        gameMasterService.progressGame(WRONG_COCKTAIL);
        when(cocktailsService.getRandomCocktail()).thenReturn(mockCocktail("ANOTHER_COCKTAIL"));
        gameMasterService.progressGame(TEST_COCKTAIL);
        GameStateResponse response = gameMasterService.getGameState();

        assertEquals(200, (int) response.getCode());
        assertEquals(4, (int) response.getGameState().getCurrentScore());
    }

    @Test
    public void getGameState_ReturnsCorrectGameStateResponse_IfGameNotRunning() {
        GameStateResponse response = gameMasterService.getGameState();

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_NOT_STARTED", response.getGameStatus());
        assertNull(response.getGameState());
    }

    @Test
    public void getGameState_ReturnsCorrectGameStateResponse_IfGameRunning() {
        gameMasterService.startGame();
        GameStateResponse response = gameMasterService.getGameState();

        assertEquals(200, (int) response.getCode());
        assertEquals("GAME_RUNNING", response.getGameStatus());
        assertNotNull(response.getGameState());
    }

    private CocktailDto mockCocktail(String drinkName) {
        return new CocktailDto(
            "123",
            drinkName,
            "Test Instructions",
            "Test Glass",
            "Test Ingredients1",
            "Test Ingredients2",
            "Test Ingredients3",
            "Test Ingredients4",
            "Test Ingredients5",
            "Test Image",
            "Test Category",
            "Test Alcoholic"
        );
    }
}
