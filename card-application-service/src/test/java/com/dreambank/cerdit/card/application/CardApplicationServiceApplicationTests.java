package com.dreambank.cerdit.card.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CardApplicationServiceApplication.class, args = "--application.args=test", useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
class CardApplicationServiceApplicationTests {

	@Test
	void contextLoads() {

	}

}
