package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDate;

public record ReservedDate(LocalDate value) implements ValueObject {
    public static ReservedDate of(LocalDate value) {
        return new ReservedDate(value);
    }

    public static ReservedDate now() {
        return new ReservedDate(LocalDate.now());
    }
}