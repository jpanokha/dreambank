package com.dreambank.demo.credit.score.controller;

import com.dreambank.demo.credit.score.service.DemoCreditScoreService;
import com.dreambank.demo.credit.score.model.CreditScoreRequest;
import com.dreambank.demo.credit.score.model.CreditScoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/demo")
@RequiredArgsConstructor
@Slf4j
public class DemoCreditScoreController {

    @Autowired
    private DemoCreditScoreService demoCreditScoreService;

    /**
     * @param creditScoreRequest
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/credit-score")
    public ResponseEntity<Mono<CreditScoreResponse>> creditScore(@RequestBody CreditScoreRequest creditScoreRequest) throws ExecutionException, InterruptedException {
        log.info(creditScoreRequest.getFirstName());
        Integer creditScore = demoCreditScoreService.getCreditScore(creditScoreRequest.getSsn());
        Mono<CreditScoreResponse> creditScoreResponseMono = Mono.just( CreditScoreResponse.builder().firstName(creditScoreRequest.getFirstName())
                .lastName(creditScoreRequest.getLastName()).ssn(creditScoreRequest.getSsn())
                .creditScore(creditScore).build());

        return new ResponseEntity<Mono<CreditScoreResponse>>(creditScoreResponseMono, HttpStatus.OK);
    }




}
