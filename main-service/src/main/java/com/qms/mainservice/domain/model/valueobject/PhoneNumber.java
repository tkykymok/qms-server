package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record PhoneNumber(String value) implements ValueObject {
    public static PhoneNumber of(String value) {
        if(value == null) return null;
        return new PhoneNumber(value);
    }
}