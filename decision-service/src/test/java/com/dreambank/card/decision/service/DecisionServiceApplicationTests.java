package com.dreambank.card.decision.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DecisionServiceApplication.class, args = "--application.args=test", useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@TestPropertySource(properties = {
		"decision.service.credit.score.url.equifax=testUrl",
		"decision.service.credit.score.url.experian=testUrl",
		"decision.service.credit.score.url.transunion=testUrl"
})
class DecisionServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
