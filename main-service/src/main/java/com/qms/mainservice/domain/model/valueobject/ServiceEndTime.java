package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record ServiceEndTime(LocalTime value) implements ValueObject {
    public static ServiceEndTime of(LocalTime value) {
        return new ServiceEndTime(value);
    }

    public static ServiceEndTime now() {
        return new ServiceEndTime(LocalTime.now());
    }
}