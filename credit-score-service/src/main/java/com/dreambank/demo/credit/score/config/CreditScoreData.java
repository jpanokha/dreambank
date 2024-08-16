package com.dreambank.demo.credit.score.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "demo.credit.score")
public class CreditScoreData {
    private Map<String,Integer> data;
}
