package com.erikareale.pokemon.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

@Service
public class PokeApiInternalService {

    private static final Logger logger = LoggerFactory.getLogger(PokeApiInternalService.class);

    @Autowired private PokeApiClient pokeApiClient;

    public Mono<PokemonSpecies> getPokemonSpecies(String name) {
        logger.info("Getting pokemon species for {}", name);
        return pokeApiClient.getResource(PokemonSpecies.class, name);
    }
}
