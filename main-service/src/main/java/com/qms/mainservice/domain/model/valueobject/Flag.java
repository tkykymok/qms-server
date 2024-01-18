package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Flag(boolean value) implements ValueObject {
    public static Flag of(boolean value) {
        return new Flag(value);
    }

    public static Flag OFF() {
        return new Flag(false);
    }

    public static Flag ON() {
        return new Flag(true);
    }
}