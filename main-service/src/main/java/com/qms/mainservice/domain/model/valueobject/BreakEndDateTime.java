package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record BreakEndDateTime(LocalDateTime value) implements ValueObject {
    public static BreakEndDateTime of(LocalDateTime value) {
        if(value == null) return null;
        return new BreakEndDateTime(value);
    }
}