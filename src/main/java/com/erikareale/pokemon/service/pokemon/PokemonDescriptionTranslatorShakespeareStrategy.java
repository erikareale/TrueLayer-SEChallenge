package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import skaro.pokeapi.resource.FlavorText;

public class PokemonDescriptionTranslatorShakespeareStrategy implements PokemonDescriptionTranslatorStrategy {

    @Override
    public String translate(FlavorText description) {
        return "";
    }

    @Override
    public PokemonDescriptionTranslationEnum getPokemonDescriptionTranslation() {
        return PokemonDescriptionTranslationEnum.SHAKESPEARE;
    }

}
