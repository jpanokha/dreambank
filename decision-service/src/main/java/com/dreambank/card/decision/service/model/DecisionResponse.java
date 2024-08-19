package com.dreambank.card.decision.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class DecisionResponse {

    private DecisionStatus status;
    private Integer equifaxCreditScore;
    private Integer experianCreditScore;
    private Integer transUnionCreditScore;



}
