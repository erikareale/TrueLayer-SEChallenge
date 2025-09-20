package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import skaro.pokeapi.resource.FlavorText;

public interface PokemonDescriptionTranslatorStrategy {
    String translate(FlavorText description);

    PokemonDescriptionTranslationEnum getPokemonDescriptionTranslation();
}
