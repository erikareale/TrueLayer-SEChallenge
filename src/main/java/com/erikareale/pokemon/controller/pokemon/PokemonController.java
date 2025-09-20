package com.erikareale.pokemon.controller.pokemon;

import com.erikareale.pokemon.service.pokemon.PokemonService;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{name}")
    public Mono<PokemonSpeciesBean> loadPokemonSpecies(@PathVariable String name) {
        return pokemonService.getPokemonSpecies(name);
    }
}
