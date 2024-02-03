package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.StoreStaffKey;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;


@Getter
public class StoreStaff extends CompositeKeyBaseEntity<StoreStaffKey> {
    private Staff staff;

    private StoreStaff() {
    }

    public static StoreStaff create(
            StaffId staffId,
            StoreId storeId
    ) {
        StoreStaff storeStaff = new StoreStaff();
        storeStaff.key = new StoreStaffKey(staffId, storeId);
        return storeStaff;
    }

    // DBから取得したレコードをStoreStaffに変換する
    public static StoreStaff reconstruct(
            StaffId staffId,
            StoreId storeId,
            TrackingInfo trackingInfo,
            Staff staff
    ) {
        StoreStaff storeStaff = new StoreStaff();
        storeStaff.key = new StoreStaffKey(staffId, storeId);
        storeStaff.trackingInfo = trackingInfo;
        storeStaff.staff = staff;
        return storeStaff;
    }
}
