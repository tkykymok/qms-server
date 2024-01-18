package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record ReservedDateTime(LocalDateTime value) implements ValueObject {
    public static ReservedDateTime of(LocalDateTime value) {
        return new ReservedDateTime(value);
    }
}