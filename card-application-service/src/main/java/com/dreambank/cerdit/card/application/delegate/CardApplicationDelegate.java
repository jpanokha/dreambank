package com.dreambank.cerdit.card.application.delegate;

import com.dreambank.cerdit.card.application.data.AddressData;
import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.model.CardApplicationRequest;
import com.dreambank.cerdit.card.application.model.CardApplicationResponse;
import com.dreambank.cerdit.card.application.model.CreditScoreRequest;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import com.dreambank.cerdit.card.application.repository.CardApplicationRepository;
import com.dreambank.cerdit.card.application.service.CardApplicationService;
import com.dreambank.cerdit.card.application.service.WebClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardApplicationDelegate {

    Mono<CardApplicationResponse> processApplication(CardApplicationRequest cardApplicationRequest);

    Flux<CardApplicationData> findAll();

    Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName);

    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Default  implements CardApplicationDelegate {
        @NonNull
        private WebClientService webClientService;

        @NonNull
        private CardApplicationService cardApplicationService;

        @Override
        public Mono<CardApplicationResponse>  processApplication(CardApplicationRequest cardApplicationRequest) {
            CardApplicationData cardApplicationData = buildCardApplicationData(cardApplicationRequest);
            //Card Application Received
            Mono<CardApplicationData> saveProcessMono = cardApplicationService.save(cardApplicationData);
            //Credit Check Starts
            Mono<DecisionResponse> creditScoreMono = webClientService.getCreditScore(CreditScoreRequest.builder()
                    .firstName(cardApplicationData.getFirstName())
                    .lastName(cardApplicationData.getLastName())
                    .ssn(cardApplicationData.getSsn())
                    .build());
            //Credit Check Complete
            Mono<CardApplicationData> cardDataMono = Mono.zip(saveProcessMono,creditScoreMono, createResponse())
                    .map(tuple->{
                        CardApplicationData cardData= tuple.getT1();
                        cardData.setStatus(tuple.getT2().getStatus());
                        cardData.setEquifaxScore(tuple.getT2().getEquifaxCreditScore());
                        cardData.setExperianScore(tuple.getT2().getExperianCreditScore());
                        cardData.setTransunionScore(tuple.getT2().getTransUnionCreditScore());
                        cardApplicationService.save(cardData).subscribe();
                        return cardData;
                    });

            return cardDataMono.map(this::buildCardApplicationResponse);
        }

        private Mono<CardApplicationResponse> createResponse(){
            return Mono.just(CardApplicationResponse.builder().build());
        }

        @Override
        public Flux<CardApplicationData> findAll() {
            Flux<CardApplicationData> applicationFLux = cardApplicationService.findAll();
            return applicationFLux
                    .log();
        }

        @Override
        public Flux<CardApplicationData> findCardApplicationByName(String firstName, String lastName) {
            return cardApplicationService.findCardApplicationByName(firstName, lastName);
        }


        private CardApplicationData buildCardApplicationData(CardApplicationRequest cardApplicationRequest){
            log.info("Card Application Request:{}"  , cardApplicationRequest.toString());
            CardApplicationData cardApplicationData = new CardApplicationData();
            AddressData addressData = new AddressData();
            BeanUtils.copyProperties(cardApplicationRequest.getAddress(), addressData);
            BeanUtils.copyProperties(cardApplicationRequest,cardApplicationData);
            cardApplicationData.setAddress(addressData);
            log.info("Card Application Data:{}", cardApplicationData.toString());
            return cardApplicationData;
        }

        private CardApplicationResponse buildCardApplicationResponse(CardApplicationData cardApplicationData){
            return CardApplicationResponse.builder()
                    .name(cardApplicationData.getFirstName() + " " + cardApplicationData.getLastName())
                    .status(cardApplicationData.getStatus())
                    .experianCreditScore(cardApplicationData.getExperianScore())
                    .equifaxCreditScore(cardApplicationData.getEquifaxScore())
                    .transUnionCreditScore(cardApplicationData.getTransunionScore())
                    .build();
        }

    }
}
