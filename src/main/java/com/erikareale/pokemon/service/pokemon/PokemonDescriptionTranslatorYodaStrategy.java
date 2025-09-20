package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.internal.FunTranslationsInternalService;
import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.FlavorText;

@Component
public class PokemonDescriptionTranslatorYodaStrategy implements PokemonDescriptionTranslatorStrategy {

    @Autowired FunTranslationsInternalService funTranslationsInternalService;

    @Override
    public Mono<String> translate(String description) {
        return funTranslationsInternalService.yodaTranslation(description);
    }

    @Override
    public PokemonDescriptionTranslationEnum getPokemonDescriptionTranslation() {
        return PokemonDescriptionTranslationEnum.YODA;
    }

}
