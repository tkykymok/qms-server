package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Address(String value) implements ValueObject {
    public static Address of(String value) {
        if(value == null) return null;
        return new Address(value);
    }
}