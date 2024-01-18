package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record PostalCode(String value) implements ValueObject {
    public static PostalCode of(String value) {
        return new PostalCode(value);
    }
}