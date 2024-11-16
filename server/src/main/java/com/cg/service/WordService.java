package com.cg.service;

import com.cg.util.SetUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordService {

    private final String expectedWord;
    private StringBuilder[] hintedWord;
    private Set<Integer> usedIndices;
    private StringBuilder[] words;
    private Integer hintsPerRound;

    public WordService(String expectedWord) {
        this.expectedWord = cleanWord(expectedWord.toLowerCase());
        initializeInstance();
    }

    public String getHintedWord() {
        return Arrays.stream(hintedWord).map(StringBuilder::toString).collect(Collectors.joining("   "));
    }

    public boolean evaluateGuess(String guess) {
        return expectedWord.equals(guess.toLowerCase());
    }

    private void initializeInstance() {
        words = Arrays.stream(expectedWord.split(" ")).map(StringBuilder::new).toArray(StringBuilder[]::new);
        usedIndices = IntStream.rangeClosed(0, countCharsWithoutSpaces(words) - 1).boxed().collect(Collectors.toSet());
        hintedWord = Arrays.stream(words).map(word -> new StringBuilder("_".repeat(word.length()))).toArray(StringBuilder[]::new);
        hintsPerRound = getOptimalNumberOfHints();
        if (!expectedWord.isEmpty()) {
            addRandomHints();
        }
    }

    public void addRandomHints() {
        for (int i = 0; i < hintsPerRound; i++) {
            int randomHintIndex = SetUtil.getRandomElementAndPop(usedIndices);
            int cumulativeLength = 0;
            int wordIndex = 0;
            int charIndex = 0;

            for (int j = 0; j < words.length; j++) {
                if (randomHintIndex < cumulativeLength + words[j].length()) {
                    wordIndex = j;
                    charIndex = randomHintIndex - cumulativeLength;
                    break;
                }
                cumulativeLength += words[j].length();
            }

            hintedWord[wordIndex].setCharAt(charIndex, words[wordIndex].charAt(charIndex));
        }
    }

    private String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ").trim();
    }

    private int countCharsWithoutSpaces(StringBuilder[] words) {
        return Arrays.stream(words).mapToInt(StringBuilder::length).sum();
    }

    private int getOptimalNumberOfHints() {
        return (int) Math.ceil(countCharsWithoutSpaces(words) / 10.0);
    }
}
