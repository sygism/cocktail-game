package com.cg.dto;

import lombok.Data;

import java.util.List;

@Data
public class RandomCocktailResponse {
    private List<CocktailDto> drinks;
}
