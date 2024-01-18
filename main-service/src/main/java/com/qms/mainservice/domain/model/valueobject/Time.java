package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record Time(Integer value) implements ValueObject {
    public static Time of(Integer value) {
        return new Time(value);
    }

    public Time addTime(Time other) {
        return Time.of(this.value + other.value);
    }
}