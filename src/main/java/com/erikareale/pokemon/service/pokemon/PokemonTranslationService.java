package com.erikareale.pokemon.service.pokemon;

import com.erikareale.pokemon.service.internal.PokeApiInternalService;
import com.erikareale.pokemon.service.pokemon.bean.PokemonSpeciesBean;
import com.erikareale.pokemon.service.pokemon.converter.PokeApiPokemonSpeciesToBeanConverter;
import com.erikareale.pokemon.service.pokemon.enums.PokemonDescriptionTranslationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.FlavorText;
import skaro.pokeapi.resource.NamedApiResource;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

import java.util.Optional;

@Component
public class PokemonTranslationService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonTranslationService.class);

    private static final String YODA_TRANSLATION_HABITAT = "cave";

    @Autowired private PokeApiInternalService pokeApiInternalService;
    @Autowired private PokeApiPokemonSpeciesToBeanConverter pokeApiPokemonSpeciesToBeanConverter;
    @Autowired private PokemonDescriptionTranslatorStrategyFactory pokemonDescriptionTranslatorStrategyFactory;

    public Mono<PokemonSpeciesBean> getPokemonWithTranslatedDescription(String name) {
        return pokeApiInternalService.getPokemonSpecies(name)
                                     .flatMap(species -> {
                                         Mono<String> translation =
                                                 pokemonDescriptionTranslatorStrategyFactory
                                                         .getPokemonDescriptionTranslatorStrategy(findTranslationStrategy(species))
                                                         .translate(getDescription(species))
                                                         .onErrorResume(e -> pokemonDescriptionTranslatorStrategyFactory
                                                                 .getPokemonDescriptionTranslatorStrategy(PokemonDescriptionTranslationEnum.DEFAULT)
                                                                 .translate(getDescription(species)));

                                         return translation.map(desc ->
                                                                        pokeApiPokemonSpeciesToBeanConverter.convert(species, desc)
                                         );
                                     });
    }

    private PokemonDescriptionTranslationEnum findTranslationStrategy(PokemonSpecies species) {
        if (isYogaTranslation(species)) {
            logger.info("Yoga translation");
            return PokemonDescriptionTranslationEnum.YODA;
        }
        logger.info("Shakespeare translation");
        return PokemonDescriptionTranslationEnum.SHAKESPEARE;
    }

    private boolean isYogaTranslation(PokemonSpecies species) {
        String habitat = Optional.ofNullable(species.getHabitat())
                                 .map(NamedApiResource::getName)
                                 .orElse(null);
        boolean isLegendary = Boolean.TRUE.equals(species.getIsLegendary());

        return YODA_TRANSLATION_HABITAT.equalsIgnoreCase(habitat) || isLegendary;
    }

    private String getDescription(PokemonSpecies species){
        return species.getFlavorTextEntries().stream().findFirst()
               .map(FlavorText::getFlavorText)
               .orElse(null);
    }

}
