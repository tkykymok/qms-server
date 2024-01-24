package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record FirstName(String value) implements ValueObject {
    public static FirstName of(String value) {
        if(value == null) return null;
        return new FirstName(value);
    }
}