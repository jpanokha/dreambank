package com.dreambank.card.decision.service.client;

import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.CreditScoreResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TransunionApiWebClient {

    private final WebClient transUnionWebClient;

    public TransunionApiWebClient(@Qualifier("transUnionWebClient") WebClient transUnionWebClient) {
        this.transUnionWebClient = transUnionWebClient;
    }

    public Mono<Integer> inquireCreditScory(CreditScoreRequest creditScoreRequest){

        Mono<Integer> tuCreditScoreMono = Mono.empty();
        try{
            tuCreditScoreMono =this.transUnionWebClient.post().uri("/credit-score")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(creditScoreRequest), CreditScoreRequest.class)
                    .exchangeToMono(res->{
                        if (res.statusCode().is2xxSuccessful()) {
                            return res.bodyToMono(CreditScoreResponse.class)
                                    .map(CreditScoreResponse :: getCreditScore);
                        }
                        return Mono.empty();
                    }).doOnNext(creditScore->log.info("Transunion Credit Score : {}",creditScore))
                    .onErrorReturn(-1)
                    .doFinally(e-> log.info("Completed Transunion call {}", e.toString())).log();
        }catch (WebClientRequestException exception){
            log.error("Exception Occurred while calling Transunion", exception);
        }
        return tuCreditScoreMono;
    }
}
