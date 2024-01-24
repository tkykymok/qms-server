package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record ActiveStaffKey (
        StoreId storeId,
        StaffId staffId
) implements Serializable {
    public static ActiveStaffKey of(StoreId storeId, StaffId staffId) {
        if(storeId == null || staffId == null) return null;
        return new ActiveStaffKey(storeId, staffId);
    }
}
