package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record StoreMenuId(Long value) implements BaseId<Long> {
    public static StoreMenuId of(Long value) {
        return new StoreMenuId(value);
    }
}