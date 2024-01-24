package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Time(Integer value) implements ValueObject {
    public static Time of(Integer value) {
        return new Time(value);
    }

    public static Time ZERO() {
        return new Time(0);
    }

    public Time add(Time other) {
        return Time.of(this.value + other.value);
    }
}