package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record HomePageUrl(String value) implements ValueObject {
    public static HomePageUrl of(String value) {
        if(value == null) return null;
        return new HomePageUrl(value);
    }
}