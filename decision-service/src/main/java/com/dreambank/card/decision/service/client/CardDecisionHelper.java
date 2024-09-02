package com.dreambank.card.decision.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@RefreshScope
@Component
public class CardDecisionHelper {

    @Value("${decision.service.credit.score.min-score:500}")
    private  Integer minCredit;

    public String getDecision(Integer ... creditScores) {
        Optional<Integer> min = Arrays.stream(creditScores).filter(score-> score!=-1).min(Integer::compare);

        if (min.isPresent()) {
            if (min.get() > minCredit && !Arrays.asList(creditScores).contains(-1)) return "APPROVED";
            else if (min.get() < minCredit && min.get()> 0) return "DECLINED";
        }
        return "PENDING";
    }
}
