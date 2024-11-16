package com.cg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStateResponse {
    private Integer code;
    private String gameStatus;
    private GameStateDto gameState;
}
