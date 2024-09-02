package com.dreambank.cerdit.card.application.repository;




import com.dreambank.cerdit.card.application.data.CardApplicationData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;


@Repository
public interface CardApplicationRepository extends ReactiveMongoRepository<CardApplicationData,String>  {
    //@Query("firstName=?0 and lastName=?1")
    Flux<CardApplicationData> findByFirstNameAndLastName(String fistName, String lastName);

    //@Query("status=?0 and applicationDate=?1")
    Flux<CardApplicationData> findByStatusAndApplicationDate(String status, LocalDate applicationDate);
}
