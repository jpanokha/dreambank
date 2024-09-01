package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.data.AddressData;
import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.model.CardApplicationRequest;
import com.dreambank.cerdit.card.application.model.CreditScoreRequest;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import com.dreambank.cerdit.card.application.repository.CardApplicationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


public interface CardApplicationService {
    Mono<CardApplicationData> save( CardApplicationData cardApplicationData);

    Flux<CardApplicationData> findAll();

    Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName);


    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Default  implements  CardApplicationService {

        @NonNull
        private CardApplicationRepository cardApplicationRepository;

        @Override
        public Mono<CardApplicationData> save( CardApplicationData cardApplicationData ) {
            log.info("Card Application Data:{}", cardApplicationData.toString());
            return cardApplicationRepository.save(cardApplicationData);
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
