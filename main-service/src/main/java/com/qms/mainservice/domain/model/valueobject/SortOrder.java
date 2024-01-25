package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record SortOrder(Integer value) implements ValueObject {
    public static SortOrder of(Integer value) {
        return new SortOrder(value);
    }
}