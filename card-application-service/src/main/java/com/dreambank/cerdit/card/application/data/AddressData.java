package com.dreambank.cerdit.card.application.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public @Data class AddressData implements Serializable {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zip;
}
