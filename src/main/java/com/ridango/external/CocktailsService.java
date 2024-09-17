package com.ridango.external;

import com.ridango.dto.CocktailDto;
import com.ridango.dto.RandomCocktailResponse;
import com.ridango.exceptions.InternalApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CocktailsService {

    private final CocktailsApi cocktailsApi;

    public CocktailDto getRandomCocktail() {
        return Optional.ofNullable(cocktailsApi.getRandomCocktail())
                .map(RandomCocktailResponse::getDrinks)
                .filter(drinks -> !drinks.isEmpty())
                .map(List::getFirst)
                .orElseThrow(() -> new InternalApplicationException("the application did not receive a cocktail"));
    }
}
