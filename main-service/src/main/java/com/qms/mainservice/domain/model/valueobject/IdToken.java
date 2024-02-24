package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record IdToken(String value) implements ValueObject {
    public static IdToken of(String value) {
        return new IdToken(value);
    }
}