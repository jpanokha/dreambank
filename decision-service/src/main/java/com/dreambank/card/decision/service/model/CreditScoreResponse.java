package com.dreambank.card.decision.service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreResponse {
    private String firstName;
    private String lastName;
    private String ssn;
    private Integer creditScore;
}
