package com.dreambank.card.decision.service.delegate;


import com.dreambank.card.decision.service.client.CardDecisionHelper;
import com.dreambank.card.decision.service.client.EquifaxApiWebClient;
import com.dreambank.card.decision.service.client.ExperianApiWebClient;
import com.dreambank.card.decision.service.client.TransunionApiWebClient;
import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.CreditScoreResponse;
import com.dreambank.card.decision.service.model.DecisionResponse;
import com.dreambank.card.decision.service.model.DecisionStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientScoreApiDelegate {

     Mono<DecisionResponse> getDecisionResponse(CreditScoreRequest creditScoreRequest);

    @Component
    @Slf4j
    @RequiredArgsConstructor
     class Default implements ClientScoreApiDelegate{

        @NonNull
        private EquifaxApiWebClient equifaxApiWebClient;

        @NonNull
         private ExperianApiWebClient experianApiWebClient;

        @NonNull
         private TransunionApiWebClient transunionApiWebClient;

        @NonNull
         private CardDecisionHelper cardDecisionHelper;

         @Override
         public Mono<DecisionResponse> getDecisionResponse(CreditScoreRequest creditScoreRequest) {
             try {
                 Mono<Integer> experianCreditScore = experianApiWebClient.inquireExperianCreditScory(creditScoreRequest);
                 Mono<Integer> transunionCreditScore = transunionApiWebClient.inquireExperianCreditScory(creditScoreRequest);
                 Mono<Integer> equifaxCreditScore = equifaxApiWebClient.inquireExperianCreditScory(creditScoreRequest);
                 return Mono.zip(experianCreditScore,transunionCreditScore, equifaxCreditScore).flatMap(t3->{
                     Integer experianScore = t3.getT1();
                     Integer transunionScore = t3.getT2();
                     Integer equifaxScore = t3.getT3();
                     DecisionResponse response = DecisionResponse.builder()
                             .experianCreditScore(experianScore)
                             .transUnionCreditScore(transunionScore)
                             .equifaxCreditScore(equifaxScore)
                             .status(cardDecisionHelper.getDecission(experianScore,transunionScore, equifaxScore)).build();
                     return Mono.just(response);

                 });
             }
             catch (Exception ex){
                 log.error("Error",ex);
             }
            return Mono.empty();
         }
     }
}
