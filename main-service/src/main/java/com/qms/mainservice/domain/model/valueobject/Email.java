package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Email(String value) implements ValueObject {
    public static Email of(String value) {
        if(value == null) return null;
        return new Email(value);
    }
}