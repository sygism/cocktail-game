package com.cg.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WordServiceTest {

    private final String TEST_WORD = "Test Name Of Cocktail12";

    private WordService wordService;

    @Before
    public void setup() {
        wordService = new WordService(TEST_WORD);
    }

    @Test
    public void WordService_AddsExpectedNumberOfHintsToHintedWord_OnCreation() {
        assertNotNull(wordService.getHintedWord());
        assertEquals(2, getNonFillerCharacterCount(wordService.getHintedWord()));
    }

    @Test
    public void evaluateGuess_ReturnsTrue_IfGuessMatchesExpectedWord() {
        assertTrue(wordService.evaluateGuess(TEST_WORD));
    }

    @Test
    public void evaluateGuess_ReturnsTrue_IfGuessMatchesExpectedWordAndUppercase() {
        assertTrue(wordService.evaluateGuess(TEST_WORD.toUpperCase()));
    }

    @Test
    public void evaluateGuess_ReturnsFalse_IfGuessDoesNotMatchExpectedWord() {
        assertFalse(wordService.evaluateGuess("something random"));
    }

    @Test
    public void addRandomHints_AddsExpectedNumberOfHintsToHintedWord() {
        wordService.addRandomHints();
        assertEquals(4, getNonFillerCharacterCount(wordService.getHintedWord()));
    }

    private int getNonFillerCharacterCount(String word) {
        return (int) word.chars().filter(ch -> ch != '_' && ch != ' ').count();
    }
}
