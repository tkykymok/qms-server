package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Username(String value) implements ValueObject {
    public static Username of(String value) {
        return new Username(value);
    }
}