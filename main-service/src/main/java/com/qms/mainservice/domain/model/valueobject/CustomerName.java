package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record CustomerName(String value) implements ValueObject {
    public static CustomerName of(String value) {
        return new CustomerName(value);
    }
}