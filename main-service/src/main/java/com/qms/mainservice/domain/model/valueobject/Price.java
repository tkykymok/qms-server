package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.math.BigDecimal;

public record Price(BigDecimal value) implements ValueObject {
    public static Price of(BigDecimal value) {
        return new Price(value);
    }

    public static Price ZERO() {
        return new Price(BigDecimal.ZERO);
    }

    public Price add(Price other) {
        return new Price(this.value.add(other.value));
    }
}