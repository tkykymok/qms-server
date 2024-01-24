package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDate;

public record Birthday(LocalDate value) implements ValueObject {
    public static Birthday of(LocalDate value) {
        if(value == null) return null;
        return new Birthday(value);
    }
}