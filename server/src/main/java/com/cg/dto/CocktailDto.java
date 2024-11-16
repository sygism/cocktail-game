package com.cg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CocktailDto {
    private String idDrink;
    private String strDrink;
    private String strInstructions;
    private String strGlass;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strImageSource;
    private String strCategory;
    private String strAlcoholic;

    public String getIngredientsAsString() {
        return Arrays.stream(new String[]{strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5})
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}
