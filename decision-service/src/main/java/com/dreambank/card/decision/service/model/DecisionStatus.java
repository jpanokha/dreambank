package com.dreambank.card.decision.service.model;

import com.fasterxml.jackson.annotation.JsonValue;


public enum DecisionStatus {

        APPROVED("APPROVED : Card Application Validated and Approved"),
        DECLINED("DECLINED : Card Application Validated and Denied"),
        PENDING("PENDING : Card Application Validation Pending");

    private final String code;

    @JsonValue
    public String getCode(){
        return code;
    }

    private DecisionStatus(String code) {
        this.code=code;
    }

    public static DecisionStatus getBYCode(String code){
        for(DecisionStatus type : values()){
            if(type.code.equals(code)){
                return type;
            }
        }
        return null;
    }


}
