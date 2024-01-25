package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record BreakStartTime(LocalTime value) implements ValueObject {
    public static BreakStartTime of(LocalTime value) {
        return new BreakStartTime(value);
    }
}