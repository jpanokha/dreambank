package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.constant.ApplicationStatus;
import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.repository.CardApplicationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


public interface CardApplicationService {
    Mono<ApplicationStatus> processApplication(CardApplicationData applicationDTO);

    Flux<CardApplicationData> findAll();

    Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName);


    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Default  implements  CardApplicationService {

        @NonNull
        private CardApplicationRepository cardApplicationRepository;

        @Override
        public Mono<ApplicationStatus> processApplication(CardApplicationData cardApplicationData) {
            if (Objects.nonNull(cardApplicationData)) {
                Mono<CardApplicationData> monoApp = cardApplicationRepository.save(cardApplicationData);
                monoApp.doFinally(m -> log.info("Finally Called")).log();

                monoApp.subscribe();
            }
            return Mono.just(ApplicationStatus.SUCCESS);
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
