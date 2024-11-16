package com.cg.external;

import com.cg.dto.CocktailDto;
import com.cg.dto.RandomCocktailResponse;
import com.cg.exceptions.InternalApplicationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CocktailsServiceTest {

    @Mock
    private CocktailsApi cocktailsApi;

    private CocktailsService cocktailsService;

    @Before
    public void setup() {
        cocktailsService = new CocktailsService(cocktailsApi);
        when(cocktailsApi.getRandomCocktail()).thenReturn(mockRandomCocktailResponse());
    }

    @Test
    public void getRandomCocktail_ReturnsRandomCocktail_IfRequestSuccessful() {
        CocktailDto cocktailDto = cocktailsService.getRandomCocktail();
        assertEquals("Margarita", cocktailDto.getStrDrink());
    }

    @Test
    public void getRandomCocktail_ThrowsException_IfRequestUnsuccessful() {
        when(cocktailsApi.getRandomCocktail()).thenReturn(null);
        assertThrows(InternalApplicationException.class, () -> cocktailsService.getRandomCocktail());
    }

    private RandomCocktailResponse mockRandomCocktailResponse() {
        RandomCocktailResponse randomCocktailResponse = new RandomCocktailResponse();
        CocktailDto cocktailDto = new CocktailDto();
        cocktailDto.setStrDrink("Margarita");
        randomCocktailResponse.setDrinks(List.of(cocktailDto));
        return randomCocktailResponse;
    }
}
