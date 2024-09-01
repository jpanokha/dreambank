package com.dreambank.cerdit.card.application.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardApplicationRequest {
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String ssn;
    private LocalDate birthDate;
}
