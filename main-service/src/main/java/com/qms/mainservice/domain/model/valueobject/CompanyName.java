package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record CompanyName(String value) implements ValueObject {
    public static CompanyName of(String value) {
        return new CompanyName(value);
    }
}