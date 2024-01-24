package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record StoreId(Long value) implements BaseId<Long> {
    public static StoreId of(Long value) {
        if(value == null) return null;
        return new StoreId(value);
    }
}