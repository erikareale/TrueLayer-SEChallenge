package com.erikareale.pokemon.service.pokemon.converter;

import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.FlavorText;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.language.Language;
import skaro.pokeapi.resource.pokemonhabitat.PokemonHabitat;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PokeApiPokemonSpeciesToBeanConverterTest {

    @InjectMocks PokeApiPokemonSpeciesToBeanConverter converter;

    @Test
    void convert() {
        PokemonSpecies source = new PokemonSpecies();

        // Legendary
        source.setIsLegendary(true);

        // Name
        source.setName("mewtwo");

        // Habitat
        NamedApiResource<PokemonHabitat> habitat = new NamedApiResource();
        habitat.setName("habitat");
        source.setHabitat(habitat);

        // Flavor text
        NamedApiResource<Language> language = new NamedApiResource();

        FlavorText enDescription = new FlavorText();
        language.setName("en");
        enDescription.setLanguage(language);
        enDescription.setFlavorText("flavor text en description");

        FlavorText frDescription = new FlavorText();
        language.setName("fr");
        frDescription.setLanguage(language);
        frDescription.setFlavorText("flavor text fr description");

        source.setFlavorTextEntries(List.of(enDescription, frDescription));


        PokemonSpeciesBean result = converter.convert(Mono.just(source));

        assertThat(result)
                .matches(r -> r.name().block().equals("mewtwo"))
                .matches(r -> r.habitat().block().equals("habitat"))
                .matches(r -> r.isLegendary().block())
                .matches(r -> r.description().block().equals("flavor text en description"));
    }

}
