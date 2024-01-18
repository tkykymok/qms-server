package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record Time(LocalTime value) implements ValueObject {
    public static Time of(LocalTime value) {
        return new Time(value);
    }
}