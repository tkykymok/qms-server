package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record CloseTime(LocalTime value) implements ValueObject {
    public static CloseTime of(LocalTime value) {
        return new CloseTime(value);
    }
}