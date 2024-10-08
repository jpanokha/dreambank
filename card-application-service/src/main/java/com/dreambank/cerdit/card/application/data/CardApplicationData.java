package com.dreambank.cerdit.card.application.data;


import com.bol.secure.Encrypted;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.convert.MongoValueConverter;
import org.springframework.data.mongodb.core.convert.encryption.MongoEncryptionConverter;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "card_application")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public  @Data class CardApplicationData implements Serializable {

    @Id
    private String applicationId = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;
    private AddressData address;

    @Encrypted
    private String phone;

    @Encrypted
    private String ssn;
    
    @Encrypted
    private LocalDate birthDate;

    private String status;
    private Integer equifaxScore;
    private Integer experianScore;
    private Integer transunionScore;

    private LocalDate applicationDate = LocalDate.now();



}
