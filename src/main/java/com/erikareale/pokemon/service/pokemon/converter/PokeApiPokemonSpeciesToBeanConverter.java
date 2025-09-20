package com.erikareale.pokemon.service.pokemon.converter;

import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBeanBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.FlavorText;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

@Service
public class PokeApiPokemonSpeciesToBeanConverter implements Converter<Mono<PokemonSpecies>, PokemonSpeciesBean> {

    @Override
    public PokemonSpeciesBean convert(Mono<PokemonSpecies> source) {
        return new PokemonSpeciesBeanBuilder()
                .withName(source.map(PokemonSpecies::getName))
                .withHabitat(source.mapNotNull(PokemonSpecies::getHabitat)
                                   .map(NamedApiResource::getName))
                .withIsLegendary(source.map(PokemonSpecies::getIsLegendary))
                .withDescription(source.mapNotNull(PokemonSpecies::getFlavorTextEntries)
                                       .flatMapMany(Flux::fromIterable)
                                       .next()
                                       .mapNotNull(FlavorText::getFlavorText))
                .build();
    }

}
