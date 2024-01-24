package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record MenuKey(
        StoreId storeId,
        StoreMenuId storeMenuId
) implements Serializable {
    public static MenuKey of(StoreId storeId, StoreMenuId storeMenuId) {
        if(storeId == null || storeMenuId == null) return null;
        return new MenuKey(storeId, storeMenuId);
    }
}
