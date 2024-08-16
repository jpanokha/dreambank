package com.dreambank.demo.credit.score.service;

import com.dreambank.demo.credit.score.config.CreditScoreData;
import com.dreambank.demo.credit.score.controller.DemoCreditScoreController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static  org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@WebFluxTest(DemoCreditScoreService.class)
class DemoCreditScoreServiceTest {
    @Autowired
    DemoCreditScoreService service;

    @MockBean
    CreditScoreData creditScoreData;

    @BeforeEach
    void setUp() {
        HashMap<String ,Integer> data = new HashMap<>();
        data.put("345-47-1145", 566);
        data.put("456-23-6780", 651);
        Mockito.when(creditScoreData.getData()).thenReturn(data);
    }

    @Test
    void testGetCreditScoreDataFound() {
        Integer creditScore = service.getCreditScore("456-23-6780");
        assertEquals(creditScore,651);
        creditScore = service.getCreditScore("345-47-1145");
        assertEquals(creditScore,566);
    }

    @Test
    void testGetCreditScoreDataNotFound() {
        Integer creditScore = service.getCreditScore("456-23-6789");
        assertNotNull(creditScore);
        assertTrue(creditScore>= 350);
        assertTrue(creditScore<= 800);
    }
}