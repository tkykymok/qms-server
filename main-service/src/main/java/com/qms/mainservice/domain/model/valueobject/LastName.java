package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record LastName(String value) implements ValueObject {
    public static LastName of(String value) {
        return new LastName(value);
    }
}