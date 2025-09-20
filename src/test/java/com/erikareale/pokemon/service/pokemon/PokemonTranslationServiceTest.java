package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.internal.PokeApiInternalService;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.converter.PokeApiPokemonSpeciesToBeanConverter;
import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PokemonTranslationServiceTest {

    @Mock private PokeApiInternalService pokeApiInternalService;
    @Mock private PokeApiPokemonSpeciesToBeanConverter pokeApiPokemonSpeciesToBeanConverter;
    @Mock private PokemonDescriptionTranslatorStrategyFactory pokemonDescriptionTranslatorStrategyFactory;

    @Mock private PokemonDescriptionTranslatorStrategy translatorYoda;
    @Mock private PokemonDescriptionTranslatorStrategy translatorShakespeare;
    @Mock private PokemonDescriptionTranslatorStrategy translatorDefault;

    @InjectMocks private PokemonTranslationService service;

    @Test
    void yodaTranslationLegendary() {
        PokemonSpecies species = new PokemonSpecies();
        species.setName("mewtwo");
        NamedApiResource legendaryHabitat = new NamedApiResource();
        legendaryHabitat.setName("habitat");
        species.setHabitat(legendaryHabitat);
        species.setIsLegendary(true);

        when(pokeApiInternalService.getPokemonSpecies("mewtwo")).thenReturn(Mono.just(species));
        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.YODA))
                .thenReturn(translatorYoda);
        when(translatorYoda.translate("mewtwo")).thenReturn(Mono.just("Yoda text"));
        when(pokeApiPokemonSpeciesToBeanConverter.convert(species, "Yoda text"))
                .thenReturn(new PokemonSpeciesBean("mewtwo", false, "cave", "Yoda text"));

        service.getPokemonWithTranslatedDescription("mewtwo").block();

        verify(translatorYoda).translate("mewtwo");
        verify(translatorShakespeare, never()).translate("mewtwo");
        verify(translatorDefault, never()).translate("mewtwo");
    }

    @Test
    void yodaTranslationCave() {
        PokemonSpecies species = new PokemonSpecies();
        species.setName("mewtwo");
        NamedApiResource caveHabitat = new NamedApiResource();
        caveHabitat.setName("cave");
        species.setHabitat(caveHabitat);
        species.setIsLegendary(false);

        when(pokeApiInternalService.getPokemonSpecies("mewtwo")).thenReturn(Mono.just(species));
        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.YODA))
                .thenReturn(translatorYoda);
        when(translatorYoda.translate("mewtwo")).thenReturn(Mono.just("Yoda text"));
        when(pokeApiPokemonSpeciesToBeanConverter.convert(species, "Yoda text"))
                .thenReturn(new PokemonSpeciesBean("mewtwo", false, "cave", "Yoda text"));

        service.getPokemonWithTranslatedDescription("mewtwo").block();

        verify(translatorYoda).translate("mewtwo");
        verify(translatorShakespeare, never()).translate("mewtwo");
        verify(translatorDefault, never()).translate("mewtwo");
    }

    @Test
    void yodaTranslationCaveAndLegenday() {
        PokemonSpecies species = new PokemonSpecies();
        species.setName("mewtwo");
        NamedApiResource caveAndLegendaryHabitat = new NamedApiResource();
        caveAndLegendaryHabitat.setName("cave");
        species.setHabitat(caveAndLegendaryHabitat);
        species.setIsLegendary(true);

        when(pokeApiInternalService.getPokemonSpecies("mewtwo")).thenReturn(Mono.just(species));
        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.YODA))
                .thenReturn(translatorYoda);
        when(translatorYoda.translate("mewtwo")).thenReturn(Mono.just("Yoda text"));
        when(pokeApiPokemonSpeciesToBeanConverter.convert(species, "Yoda text"))
                .thenReturn(new PokemonSpeciesBean("mewtwo", false, "cave", "Yoda text"));

        service.getPokemonWithTranslatedDescription("mewtwo").block();

        verify(translatorYoda).translate("mewtwo");
        verify(translatorShakespeare, never()).translate("mewtwo");
        verify(translatorDefault, never()).translate("mewtwo");
    }

    @Test
    void shakespeareTranslation() {
        PokemonSpecies species = new PokemonSpecies();
        species.setName("mewtwo");
        NamedApiResource habitat = new NamedApiResource();
        habitat.setName("habitat");
        species.setHabitat(habitat);
        species.setIsLegendary(false);

        when(pokeApiInternalService.getPokemonSpecies("mewtwo")).thenReturn(Mono.just(species));
        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.SHAKESPEARE))
                .thenReturn(translatorShakespeare);
        when(translatorShakespeare.translate("mewtwo")).thenReturn(Mono.just("Shakespeare text"));
        when(pokeApiPokemonSpeciesToBeanConverter.convert(species, "Shakespeare text"))
                .thenReturn(new PokemonSpeciesBean("mewtwo", false, "cave", "Shakespeare text"));

        service.getPokemonWithTranslatedDescription("mewtwo").block();

        verify(translatorYoda, never()).translate("mewtwo");
        verify(translatorShakespeare).translate("mewtwo");
        verify(translatorDefault, never()).translate("mewtwo");
    }

    @Test
    void fallbackTranslation() {
        PokemonSpecies species = new PokemonSpecies();
        species.setName("pikachu");
        NamedApiResource habitat = new NamedApiResource();
        habitat.setName("forest");
        species.setHabitat(habitat);
        species.setIsLegendary(false);

        when(pokeApiInternalService.getPokemonSpecies(any())).thenReturn(Mono.just(species));
        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.SHAKESPEARE))
                .thenReturn(translatorShakespeare);
        when(translatorShakespeare.translate(any())).thenReturn(Mono.error(new RuntimeException()));

        when(pokemonDescriptionTranslatorStrategyFactory.getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.DEFAULT))
                .thenReturn(translatorDefault);
        when(translatorDefault.translate(any())).thenReturn(Mono.just("Default text"));

        when(pokeApiPokemonSpeciesToBeanConverter.convert(species, "Default text"))
                .thenReturn(new PokemonSpeciesBean("pikachu", false, "forest", "Default text"));

        service.getPokemonWithTranslatedDescription("pikachu").block();

        verify(translatorShakespeare).translate("pikachu");
        verify(translatorDefault).translate("pikachu");
        verify(translatorYoda, never()).translate("pikachu");
    }

}
