package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record HoldStartDateTime(LocalDateTime value) implements ValueObject {
    public static HoldStartDateTime of(LocalDateTime value) {
        return new HoldStartDateTime(value);
    }
}