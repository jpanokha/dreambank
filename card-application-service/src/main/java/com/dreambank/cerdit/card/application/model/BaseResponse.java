package com.dreambank.cerdit.card.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@SuperBuilder
public class BaseResponse {
    private String responseStatus;
    private String message;

}
