package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.model.CreditScoreRequest;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientService {

    @NonNull
    private final WebClient webClient;

    public Mono<DecisionResponse> getCreditScore(CreditScoreRequest creditScoreRequest){
        Mono<DecisionResponse> DecisionResponseMono = this.webClient.post().uri("/inquireCreditApproval")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(creditScoreRequest), CreditScoreRequest.class)
                .exchangeToMono(res->{
                    if (res.statusCode().is2xxSuccessful()) {
                        return res.bodyToMono(DecisionResponse.class);
                    }
                    return Mono.empty();
                } );

        DecisionResponseMono.doOnNext(e -> log.info(e.toString())).log();
       // creditScoreResponseMono.subscribe();
        return DecisionResponseMono;
    }

}
