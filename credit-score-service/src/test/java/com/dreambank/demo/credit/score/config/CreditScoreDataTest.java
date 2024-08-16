package com.dreambank.demo.credit.score.config;

import com.dreambank.demo.credit.score.controller.DemoCreditScoreController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@WebFluxTest(CreditScoreData.class)
@ActiveProfiles(value = "test")
@TestPropertySource("classpath:application-test.yml")
class CreditScoreDataTest {

    @Autowired
    private CreditScoreData creditScoreData;

    @Test
    void getData() {
        Assertions.assertNotNull(creditScoreData);
    }

}