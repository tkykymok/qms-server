package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record ServiceEndDateTime(LocalDateTime value) implements ValueObject {
    public static ServiceEndDateTime of(LocalDateTime value) {
        return new ServiceEndDateTime(value);
    }
}