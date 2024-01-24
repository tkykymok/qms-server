package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record ServiceStartDateTime(LocalDateTime value) implements ValueObject {
    public static ServiceStartDateTime of(LocalDateTime value) {
        if(value == null) return null;
        return new ServiceStartDateTime(value);
    }

    public static ServiceStartDateTime now() {
        return new ServiceStartDateTime(LocalDateTime.now());
    }
}