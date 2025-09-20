package com.erikareale.pokemon.service.pokemon.bean;

public final class PokemonSpeciesBeanBuilder {

    private String description;
    private String name;
    private Boolean isLegendary;
    private String habitat;

    public PokemonSpeciesBeanBuilder() {}

    public PokemonSpeciesBeanBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PokemonSpeciesBeanBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PokemonSpeciesBeanBuilder withIsLegendary(Boolean isLegendary) {
        this.isLegendary = isLegendary;
        return this;
    }

    public PokemonSpeciesBeanBuilder withHabitat(String habitat) {
        this.habitat = habitat;
        return this;
    }

    public PokemonSpeciesBean build() {return new PokemonSpeciesBean(name, isLegendary, habitat, description);}

}
