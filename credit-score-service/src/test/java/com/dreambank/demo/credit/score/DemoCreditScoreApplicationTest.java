package com.dreambank.demo.credit.score;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoCreditScoreApplication.class, args = "--application.args=test", useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
class DemoCreditScoreApplicationTest {

    @Test
    public void contextLoads(@Autowired ApplicationArguments args) {
        assertTrue(args.containsOption("application.args"));
        assertEquals(args.getOptionNames().size() , 1);
    }

    @Test
    void main() {
    }
}