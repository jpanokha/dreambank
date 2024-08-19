package com.dreambank.cerdit.card.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${application.decision.service.url}")
    private String decisionServiceUrl;



    @Bean
    public WebClient webClient() {
        log.info("Decision Service Url : {}", decisionServiceUrl);
        return WebClient.builder().baseUrl(decisionServiceUrl).build();
    }


}
