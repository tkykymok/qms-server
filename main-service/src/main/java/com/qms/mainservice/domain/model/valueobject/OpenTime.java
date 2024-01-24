package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record OpenTime(LocalTime value) implements ValueObject {
    public static OpenTime of(LocalTime value) {
        return new OpenTime(value);
    }
}