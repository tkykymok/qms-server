package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record AccessToken(String value) implements ValueObject {
    public static AccessToken of(String value) {
        return new AccessToken(value);
    }
}