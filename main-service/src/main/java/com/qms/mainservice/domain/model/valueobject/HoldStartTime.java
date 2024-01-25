package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record HoldStartTime(LocalTime value) implements ValueObject {
    public static HoldStartTime of(LocalTime value) {
        return new HoldStartTime(value);
    }

    public static HoldStartTime now() {
        return new HoldStartTime(LocalTime.now());
    }
}