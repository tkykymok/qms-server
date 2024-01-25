package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record BreakEndTime(LocalTime value) implements ValueObject {
    public static BreakEndTime of(LocalTime value) {
        return new BreakEndTime(value);
    }
}