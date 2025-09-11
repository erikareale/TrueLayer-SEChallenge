package com.erikareale.pokemon.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skaro.pokeapi.client.PokeApiClient;

@Service
class PokeApiInternalService {

    private static final Logger logger = LoggerFactory.getLogger(PokeApiInternalService.class);

    @Autowired private PokeApiClient pokeApiClient;


}
