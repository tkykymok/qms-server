package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record ImageUrl(String value) implements ValueObject {
    public static ImageUrl of(String value) {
        return new ImageUrl(value);
    }
}