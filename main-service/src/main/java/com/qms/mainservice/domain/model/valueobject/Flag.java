package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Flag(Boolean value) implements ValueObject {
    public static Flag of(Boolean value) {
        return new Flag(value);
    }

    public static Flag fromValue(int value) {
        return new Flag(value == 1);
    }

    public static Flag OFF() {
        return new Flag(false);
    }

    public static Flag ON() {
        return new Flag(true);
    }
}