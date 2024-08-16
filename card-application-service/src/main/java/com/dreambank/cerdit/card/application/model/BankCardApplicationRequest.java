package com.dreambank.cerdit.card.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCardApplicationRequest {
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String ssn;
}
