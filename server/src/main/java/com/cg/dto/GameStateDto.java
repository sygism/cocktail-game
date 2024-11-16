package com.cg.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameStateDto {
    private Integer hintsLeft;
    private String hintedWord;
    private String[] hints;
    private String hintInstructions;
    private Integer currentScore;
}
