package com.dreambank.demo.credit.score.service;

import com.dreambank.demo.credit.score.config.CreditScoreData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DemoCreditScoreService {
    @Autowired
    private CreditScoreData creditScoreData;

    public Integer getCreditScore(String key) {
        Integer creditScore = creditScoreData.getData().get(key);
        if (creditScore == null) {
            Random random = new Random();
            creditScore = random.nextInt(350, 800);
        }
        return creditScore;
    }
}
