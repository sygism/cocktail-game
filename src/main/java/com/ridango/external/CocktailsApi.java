package com.ridango.external;
import com.ridango.configuration.FeignConfig;
import com.ridango.dto.RandomCocktailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cocktailsApi", url = "${external.cocktails_api.base_path}", configuration = FeignConfig.class)
public interface CocktailsApi {
    @GetMapping("api/json/v1/1/random.php")
    RandomCocktailResponse getRandomCocktail();
}
