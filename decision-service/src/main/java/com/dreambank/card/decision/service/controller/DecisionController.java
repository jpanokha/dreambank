package com.dreambank.card.decision.service.controller;

import com.dreambank.card.decision.service.delegate.ClientScoreApiDelegate;
import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.DecisionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/decision/")
@RequiredArgsConstructor
@Slf4j
public class DecisionController {

    @Autowired
    private ClientScoreApiDelegate clientScoreApiDelegate;

    @PostMapping("/inquireCreditApproval")
    public Mono<DecisionResponse> inquireCreditApproval(@RequestBody CreditScoreRequest creditScoreRequest) {
        return clientScoreApiDelegate.getDecisionResponse(creditScoreRequest);
    }
}
