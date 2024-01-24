package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record SalesId(Long value) implements BaseId<Long> {
    public static SalesId of(Long value) {
        if(value == null) return null;
        return new SalesId(value);
    }
}