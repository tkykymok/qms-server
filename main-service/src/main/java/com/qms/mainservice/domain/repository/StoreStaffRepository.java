package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.aggregate.StoreStaffOverview;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public interface StoreStaffRepository {
    // 店舗IDに紐づく活動スタッフ一覧を取得する
    List<StoreStaff> findAllByStoreId(StoreId storeId);

    // 活動中スタッフを更新する(DELETE -> INSERT)
    void deleteAndInsertActiveStaff(StoreStaffOverview storeStaffOverview);
}
