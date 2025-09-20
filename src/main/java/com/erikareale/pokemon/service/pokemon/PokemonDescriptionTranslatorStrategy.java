package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.FlavorText;

public interface PokemonDescriptionTranslatorStrategy {
    Mono<String> translate(String description);

    PokemonDescriptionTranslationEnum getPokemonDescriptionTranslation();
}
