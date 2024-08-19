package com.dreambank.card.decision.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${application.decision.service.credit.score.equifax.url}")
    private String equifaxCreditScoreUrl;

    @Value("${application.decision.service.credit.score.experian.url}")
    private String experianCreditScoreUrl;

    @Value("${application.decision.service.credit.score.transunion.url}")
    private String transunionCreditScoreUrl;

    @Bean("transUnionWebClient")
    public WebClient transUnionWebClient() {
        return WebClient.builder().baseUrl(transunionCreditScoreUrl).build();
    }

    @Bean("experianWebClient")
    public WebClient experianWebClient() {
        return WebClient.builder().baseUrl(experianCreditScoreUrl).build();
    }

    @Bean("equifaxWebClient")
    public WebClient equifaxWebClient() {
        return WebClient.builder().baseUrl(equifaxCreditScoreUrl).build();
    }
}
