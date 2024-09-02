package com.dreambank.card.decision.service.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CardDecisionHelper.class)
@TestPropertySource(properties = {
        "decision.service.credit.score.min-score=550",
})
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class CardDecisionHelperTest {

    @Autowired
    CardDecisionHelper helper;

    @Test
    void getDecision_Success() {

        String decision = helper.getDecision(560, 570, 555);
        assertEquals("APPROVED", decision);
    }

    @Test
    void getDecision_Failure() {

        String decision = helper.getDecision(560, 540, 555);
        assertEquals("DECLINED", decision);
    }

    @Test
    void getDecision_Pending() {

        String decision = helper.getDecision(560, 540, -1);
        assertEquals("DECLINED", decision);
    }

    @Test
    void getDecision_Pending1() {

        String decision = helper.getDecision(560, -1, 555);
        assertEquals("PENDING", decision);
    }
}