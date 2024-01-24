package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Longitude(Double value) implements ValueObject {
    public static Longitude of(Double value) {
        if(value == null) return null;
        return new Longitude(value);
    }
}