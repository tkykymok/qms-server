package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Latitude(Double value) implements ValueObject {
    public static Latitude of(Double value) {
        if(value == null) return null;
        return new Latitude(value);
    }
}