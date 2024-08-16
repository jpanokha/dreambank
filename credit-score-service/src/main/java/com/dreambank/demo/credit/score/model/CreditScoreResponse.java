package com.dreambank.demo.credit.score.model;

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
