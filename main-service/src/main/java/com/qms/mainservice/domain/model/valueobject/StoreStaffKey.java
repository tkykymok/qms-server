package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record StoreStaffKey(
        StaffId staffId,
        StoreId storeId
) implements Serializable {
    public static StoreStaffKey of(StaffId staffId, StoreId storeId) {
        return new StoreStaffKey(staffId, storeId);
    }
}
