package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record ActiveStaffKey (
        StoreId storeId,
        StaffId staffId
) implements Serializable {
    public static ActiveStaffKey of(StoreId storeId, StaffId staffId) {
        return new ActiveStaffKey(storeId, staffId);
    }
}
