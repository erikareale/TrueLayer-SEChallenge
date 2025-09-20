package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PokemonDescriptionTranslatorDefaultStrategy implements PokemonDescriptionTranslatorStrategy {

    @Override
    public Mono<String> translate(String description) {
        return Mono.just(description);
    }

    @Override
    public PokemonDescriptionTranslationEnum getPokemonDescriptionTranslation() {
        return PokemonDescriptionTranslationEnum.DEFAULT;
    }

}
