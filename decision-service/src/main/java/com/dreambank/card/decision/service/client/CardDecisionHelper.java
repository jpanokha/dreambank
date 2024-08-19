package com.dreambank.card.decision.service.client;

import com.dreambank.card.decision.service.model.DecisionResponse;
import com.dreambank.card.decision.service.model.DecisionStatus;
import com.dreambank.card.decision.service.model.CreditScoreResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CardDecisionHelper {

    public DecisionStatus getDecission(Integer ... creditScores) {
        Optional<Integer> max = Arrays.stream(creditScores).max(Integer::compare);

        if (max.isPresent()) {
            if (max.get() > 500) return DecisionStatus.APPROVED;
            else if (max.get() < 500 && max.get()> 0) return DecisionStatus.DECLINED;

        }
        return DecisionStatus.PENDING;
    }
}
