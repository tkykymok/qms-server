package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record CognitoUserId(String value) implements ValueObject {
    public static CognitoUserId of(String value) {
        return new CognitoUserId(value);
    }
}