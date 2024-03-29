package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record CustomerId(Long value) implements BaseId<Long> {
    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }
}