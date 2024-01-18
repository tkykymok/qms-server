package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record ServiceStartDateTime(LocalDateTime value) implements ValueObject {
    public static ServiceStartDateTime of(LocalDateTime value) {
        return new ServiceStartDateTime(value);
    }
}