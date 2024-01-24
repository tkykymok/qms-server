package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record StoreName(String value) implements ValueObject {
    public static StoreName of(String value) {
        if(value == null) return null;
        return new StoreName(value);
    }
}