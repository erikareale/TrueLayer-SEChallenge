package com.erikareale.pokemon.service.pokemon.bean;

import reactor.core.publisher.Mono;

public record PokemonSpeciesBean(Mono<String> name,
                                 Mono<Boolean> isLegendary,
                                 Mono<String> habitat,
                                 Mono<String> description) {
}
