package com.dreambank.cerdit.card.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@SuperBuilder
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardApplicationResponse  extends BaseResponse{

    private String name;
    private String status;
    private Integer equifaxCreditScore;
    private Integer experianCreditScore;
    private Integer transUnionCreditScore;
}
