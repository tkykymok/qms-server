package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record CognitoUserId(Long value) implements BaseId<Long> {
    public static CognitoUserId of(Long value) {
        if(value == null) return null;
        return new CognitoUserId(value);
    }
}