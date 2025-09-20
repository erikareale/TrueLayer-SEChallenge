package com.erikareale.pokemon.service.pokemon.bean;

import reactor.core.publisher.Mono;

public final class PokemonSpeciesBeanBuilder {

    private Mono<String> description;
    private Mono<String> name;
    private Mono<Boolean> isLegendary;
    private Mono<String> habitat;

    public PokemonSpeciesBeanBuilder() {}

    public PokemonSpeciesBeanBuilder withDescription(Mono<String> description) {
        this.description = description;
        return this;
    }

    public PokemonSpeciesBeanBuilder withName(Mono<String> name) {
        this.name = name;
        return this;
    }

    public PokemonSpeciesBeanBuilder withIsLegendary(Mono<Boolean> isLegendary) {
        this.isLegendary = isLegendary;
        return this;
    }

    public PokemonSpeciesBeanBuilder withHabitat(Mono<String> habitat) {
        this.habitat = habitat;
        return this;
    }

    public PokemonSpeciesBean build() {return new PokemonSpeciesBean(name, isLegendary, habitat, description);}

}
