package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.internal.PokeApiInternalService;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.converter.PokeApiPokemonSpeciesToBeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokemonService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    @Autowired private PokeApiInternalService pokeApiInternalService;
    @Autowired private PokeApiPokemonSpeciesToBeanConverter pokeApiPokemonSpeciesToBeanConverter;
    @Autowired private PokemonTranslationService pokemonTranslationService;

    public Mono<PokemonSpeciesBean> getPokemonSpecies(String name) {
        logger.info("Getting pokemon species for {}", name);

        return pokeApiInternalService.getPokemonSpecies(name)
                                     .mapNotNull(a -> pokeApiPokemonSpeciesToBeanConverter.convert(a));
    }

    public Mono<PokemonSpeciesBean> getPokemonWithTranslatedDescription(String name) {
        logger.info("Getting pokemon with translated description for {}", name);
        return pokemonTranslationService.getPokemonWithTranslatedDescription(name);
    }
}
