package com.erikareale.pokemon.service.pokemon.converter;

import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBeanBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import skaro.pokeapi.resource.FlavorText;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

import java.util.Optional;

@Service
public class PokeApiPokemonSpeciesToBeanConverter implements Converter<PokemonSpecies, PokemonSpeciesBean> {

    @Override
    public PokemonSpeciesBean convert(PokemonSpecies source) {
        return new PokemonSpeciesBeanBuilder()
                .withName(source.getName())
                .withHabitat(Optional.ofNullable(source.getHabitat())
                                     .map(NamedApiResource::getName)
                                     .orElse(null))
                .withIsLegendary(source.getIsLegendary())
                .withDescription(source.getFlavorTextEntries().stream().findFirst()
                                       .map(FlavorText::getFlavorText)
                                       .orElse(null))
                .build();
    }

    public PokemonSpeciesBean convert(PokemonSpecies source, String translation) {
        return new PokemonSpeciesBeanBuilder()
                .withName(source.getName())
                .withHabitat(Optional.ofNullable(source.getHabitat())
                                     .map(NamedApiResource::getName)
                                     .orElse(null))
                .withIsLegendary(source.getIsLegendary())
                .withDescription(translation)
                .build();
    }

}
