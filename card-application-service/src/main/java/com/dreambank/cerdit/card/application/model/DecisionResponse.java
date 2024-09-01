package com.dreambank.cerdit.card.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DecisionResponse {
    private String status;
    private Integer equifaxCreditScore;
    private Integer experianCreditScore;
    private Integer transUnionCreditScore;



}
