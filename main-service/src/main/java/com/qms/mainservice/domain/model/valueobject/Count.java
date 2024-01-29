package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Count(Integer value) implements ValueObject {
    public static Count of(Integer value) {
        return new Count(value);
    }

    public static Count ZERO() {
        return new Count(0);
    }

    public Count add(Count other) {
        return new Count(this.value + other.value);
    }
}