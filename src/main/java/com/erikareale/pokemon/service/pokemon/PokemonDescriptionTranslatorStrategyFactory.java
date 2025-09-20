package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PokemonDescriptionTranslatorStrategyFactory {

    private final Map<PokemonDescriptionTranslationEnum, PokemonDescriptionTranslatorStrategy> pokemonDescriptionTranslatorMap;

    @Autowired
    public PokemonDescriptionTranslatorStrategyFactory(List<PokemonDescriptionTranslatorStrategy> PokemonDescriptionTranslators) {
        Map<PokemonDescriptionTranslationEnum, PokemonDescriptionTranslatorStrategy> pokemonDescriptionTranslatorMap =
                new EnumMap<>(PokemonDescriptionTranslationEnum.class);
        for (PokemonDescriptionTranslatorStrategy strategy : PokemonDescriptionTranslators) {
            pokemonDescriptionTranslatorMap.put(strategy.getPokemonDescriptionTranslation(), strategy);
        }
        this.pokemonDescriptionTranslatorMap = pokemonDescriptionTranslatorMap;
    }

    public PokemonDescriptionTranslatorStrategy getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum pokemonDescriptionTranslation) {
        return Optional.ofNullable(pokemonDescriptionTranslation)
                       .map(pokemonDescriptionTranslatorMap::get)
                       .orElseThrow(() -> new IllegalArgumentException("Strategy with type: " + pokemonDescriptionTranslation + " not valid"));
    }

}
