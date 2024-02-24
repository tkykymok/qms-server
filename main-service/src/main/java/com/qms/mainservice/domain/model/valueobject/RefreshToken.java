package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record RefreshToken(String value) implements ValueObject {
    public static RefreshToken of(String value) {
        return new RefreshToken(value);
    }
}