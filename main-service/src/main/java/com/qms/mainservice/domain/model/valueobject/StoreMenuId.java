package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record StoreMenuId(Integer value) implements BaseId<Integer> {
    public static StoreMenuId of(Integer value) {
        if(value == null) return null;
        return new StoreMenuId(value);
    }
}