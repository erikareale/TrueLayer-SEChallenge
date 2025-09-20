package com.erikareale.pokemon.service.pokemon.converter;

import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
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


        PokemonSpeciesBean result = converter.convert(source);

        assertThat(result)
                .matches(r -> r.name().equals("mewtwo"))
                .matches(r -> r.habitat().equals("habitat"))
                .matches(PokemonSpeciesBean::isLegendary)
                .matches(r -> r.description().equals("flavor text en description"));
    }

    @Test
    void convertWithDescription() {
        PokemonSpecies source = new PokemonSpecies();

        // Legendary
        source.setIsLegendary(true);

        // Name
        source.setName("mewtwo");

        // Habitat
        NamedApiResource<PokemonHabitat> habitat = new NamedApiResource();
        habitat.setName("habitat");
        source.setHabitat(habitat);

        PokemonSpeciesBean result = converter.convert(source, "flavor text en description");

        assertThat(result)
                .matches(r -> r.name().equals("mewtwo"))
                .matches(r -> r.habitat().equals("habitat"))
                .matches(PokemonSpeciesBean::isLegendary)
                .matches(r -> r.description().equals("flavor text en description"));
    }
}
