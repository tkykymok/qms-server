package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.model.valueobject.StoreId;

public interface StaffRepository {

    StoreStaffOverview findStoreStaffOverview(StoreId storeId);

}
