package com.dreambank.card.decision.service.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreRequest {

    private String firstName;
    private String lastName;
    private String ssn;
}
