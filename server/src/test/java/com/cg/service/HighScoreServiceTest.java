package com.cg.service;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HighScoreServiceTest {

    private HighScoreService highScoreService;

    @Before
    public void setup() {
        highScoreService = new HighScoreService();
        String PROPERTIES_PATH = "resources/high_score.properties";
        ReflectionTestUtils.setField(highScoreService, "propertiesFilePath", PROPERTIES_PATH);
    }

    @Test
    public void getHighScore_ReturnsCorrectHighScore_IfPropertiesFilePresent() {
        int highScore = highScoreService.getHighScore();
        assertEquals(1, highScore);
    }

    @Test
    public void updateHighScore_WritesNewValueToFile_IfPropertiesFilePresent() {
        highScoreService.updateHighScore(2);
        int highScore = highScoreService.getHighScore();
        assertEquals(2, highScore);
    }

    @After
    public void reset() {
        highScoreService.updateHighScore(1);
    }
}
