package com.dreambank.cerdit.card.application.data;


import com.bol.secure.Encrypted;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.convert.MongoValueConverter;
import org.springframework.data.mongodb.core.convert.encryption.MongoEncryptionConverter;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.time.LocalDate;

@Document(collection = "card_application")

@NoArgsConstructor
@AllArgsConstructor
@Builder
public  @Data class CardApplicationData implements Serializable {

    private String firstName;
    private String lastName;
    private AddressData address;

    @Encrypted
    private String phone;

    @Encrypted
    private String ssn;

    private String applicationType;
    private LocalDate applicationDate;
    private String applicationStatus;


}
