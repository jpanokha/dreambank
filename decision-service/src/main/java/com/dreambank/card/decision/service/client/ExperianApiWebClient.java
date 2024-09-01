package com.dreambank.card.decision.service.client;

import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.CreditScoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ExperianApiWebClient {

    @Autowired()
    @Qualifier("experianWebClient")
    private WebClient experianWebClient;

    public Mono<Integer> inquireExperianCreditScory(CreditScoreRequest creditScoreRequest){

        Mono<Integer> exCreditScoreMono = Mono.empty();
        try{
            exCreditScoreMono =this.experianWebClient.post().uri("/credit-score")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(creditScoreRequest), CreditScoreRequest.class)
                    .exchangeToMono(res->{
                        if (res.statusCode().is2xxSuccessful()) {
                            return res.bodyToMono(CreditScoreResponse.class)
                                    .map(CreditScoreResponse :: getCreditScore);
                        }
                        return Mono.empty();
                    }).doOnNext(creditScore->log.info("Experian Credit Score : {}",creditScore))
                    .onErrorReturn(-1)
                    .doFinally(e-> log.info("Completed Experian call {}", e.toString())).log();
        }catch (WebClientRequestException exception){
            log.error("Exception Occurred while calling Experian", exception);
        }
        return exCreditScoreMono;
    }
}
