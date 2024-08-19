package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.model.CreditScoreRequest;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import com.dreambank.cerdit.card.application.repository.CardApplicationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


public interface CardApplicationService {
    Mono<DecisionResponse> processApplication(CardApplicationData applicationDTO);

    Flux<CardApplicationData> findAll();

    Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName);


    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Default  implements  CardApplicationService {
        @NonNull
        private WebClientService webClientService;

        @NonNull
        private CardApplicationRepository cardApplicationRepository;

        @Override
        public Mono<DecisionResponse> processApplication(CardApplicationData cardApplicationData) {

            Mono<DecisionResponse> response = null;
            if (Objects.nonNull(cardApplicationData)) {
                 response = webClientService.getCreditScore(CreditScoreRequest.builder()
                         .firstName(cardApplicationData.getFirstName())
                         .lastName(cardApplicationData.getLastName())
                         .ssn(cardApplicationData.getSsn())
                         .build());
                response.doOnNext(e->log.info(e.getStatus().getCode())).log();

                Mono<CardApplicationData> monoApp = cardApplicationRepository.save(cardApplicationData);
                monoApp.doFinally(m -> log.info("Finally Called")).log();
               //response.zipWith(monoApp);
                monoApp.subscribe();
                response.subscribe();
            }
            return response;//Mono.just(ApplicationStatus.SUCCESS);
        }

        @Override
        public Flux<CardApplicationData> findAll() {
            Flux<CardApplicationData> applicationFLux = cardApplicationRepository.findAll();
            return applicationFLux
                    .log();
        }

        @Override
        public Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName) {
            return cardApplicationRepository.findByFisrtNameAndLastName(firstName, lastName);
        }


    }
}
