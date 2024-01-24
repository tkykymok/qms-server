package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record LastName(String value) implements ValueObject {
    public static LastName of(String value) {
        if(value == null) return null;
        return new LastName(value);
    }
}