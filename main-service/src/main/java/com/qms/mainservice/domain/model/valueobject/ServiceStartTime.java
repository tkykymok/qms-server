package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;

public record ServiceStartTime(LocalTime value) implements ValueObject {
    public static ServiceStartTime of(LocalTime value) {
        return new ServiceStartTime(value);
    }

    public static ServiceStartTime now() {
        return new ServiceStartTime(LocalTime.now());
    }
}