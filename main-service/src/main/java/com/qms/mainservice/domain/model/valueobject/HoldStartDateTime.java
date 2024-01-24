package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record HoldStartDateTime(LocalDateTime value) implements ValueObject {
    public static HoldStartDateTime of(LocalDateTime value) {
        if(value == null) return null;
        return new HoldStartDateTime(value);
    }

    public static HoldStartDateTime now() {
        return new HoldStartDateTime(LocalDateTime.now());
    }
}