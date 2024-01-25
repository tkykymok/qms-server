package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record MenuName(String value) implements ValueObject {
    public static MenuName of(String value) {
        return new MenuName(value);
    }
}