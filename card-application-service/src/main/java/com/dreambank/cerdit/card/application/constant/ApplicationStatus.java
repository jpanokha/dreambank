package com.dreambank.cerdit.card.application.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    PENDING("PENDING"),
    ;

    private String code;

    @JsonValue
    public String getCode(){
        return code;
    }

    private ApplicationStatus(String code) {
        this.code=code;
    }

    public static ApplicationStatus getBYCode(String code){
        for(ApplicationStatus type : values()){
            if(type.code.equals(code)){
                return type;
            }
        }
        return null;
    }
}
