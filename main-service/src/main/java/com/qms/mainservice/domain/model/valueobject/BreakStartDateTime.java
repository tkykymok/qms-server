package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record BreakStartDateTime(LocalDateTime value) implements ValueObject {
    public static BreakStartDateTime of(LocalDateTime value) {
        if(value == null) return null;
        return new BreakStartDateTime(value);
    }
}