package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Password(String value) implements ValueObject {
    public static Password of(String value) {
        return new Password(value);
    }
}