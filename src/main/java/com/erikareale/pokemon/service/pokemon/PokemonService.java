package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.internal.PokeApiInternalService;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.converter.PokeApiPokemonSpeciesToBeanConverter;
import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

import java.util.Optional;

@Service
public class PokemonService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    private static final String YODA_TRANSLATION_HABITAT = "cave";

    @Autowired private PokeApiInternalService pokeApiInternalService;
    @Autowired private PokeApiPokemonSpeciesToBeanConverter pokeApiPokemonSpeciesToBeanConverter;
    @Autowired private PokemonDescriptionTranslatorStrategyFactory pokemonDescriptionTranslatorStrategyFactory;

    public Mono<PokemonSpeciesBean> getPokemonSpecies(String name) {
        logger.info("Getting pokemon species for {}", name);

        return pokeApiInternalService.getPokemonSpecies(name)
                                     .mapNotNull(a -> pokeApiPokemonSpeciesToBeanConverter.convert(a));
    }

    public Mono<PokemonSpeciesBean> getPokemonWithTranslatedDescription(String name) {
        return pokeApiInternalService.getPokemonSpecies(name)
                                     .flatMap(species -> {
                                         Mono<String> translation =
                                                 pokemonDescriptionTranslatorStrategyFactory
                                                         .getPokemonDescriptionTranslatorStrategy(
                                                                 isYogaTranslation(species) ? PokemonDescriptionTranslationEnum.YODA
                                                                                            : PokemonDescriptionTranslationEnum.SHAKESPEARE)
                                                         .translate(species.getName());

                                         return translation.map(desc ->
                                                                        pokeApiPokemonSpeciesToBeanConverter.convert(species, desc)
                                         );
                                     });
    }

    private boolean isYogaTranslation(PokemonSpecies species) {
        String habitat = Optional.ofNullable(species.getHabitat())
                                 .map(NamedApiResource::getName)
                                 .orElse(null);
        boolean isLegendary = Boolean.TRUE.equals(species.getIsLegendary());

        return YODA_TRANSLATION_HABITAT.equalsIgnoreCase(habitat) || isLegendary;
    }
}
