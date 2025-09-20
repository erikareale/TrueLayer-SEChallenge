package com.erikareale.pokemon.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FunTranslationsInternalService {

    private static final Logger logger = LoggerFactory.getLogger(FunTranslationsInternalService.class);

    private final WebClient webClient =
            WebClient.create("https://api.funtranslations.com/translate");

    public Mono<String> yodaTranslation(String text) {
        logger.info("Yoda translation of {}", text);
        return webClient.post()
                        .uri("/yoda.json")
                        .bodyValue("text=" + text)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .retrieve()
                        .bodyToMono(String.class);
    }

    public Mono<String> shakespeareTranslation(String text) {
        logger.info("Shakespeare translation of {}", text);
        return webClient.post()
                        .uri("/shakespeare.json")
                        .bodyValue("text=" + text)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .retrieve()
                        .bodyToMono(String.class);
    }

}
