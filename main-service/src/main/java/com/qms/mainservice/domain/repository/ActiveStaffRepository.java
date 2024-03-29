package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public interface ActiveStaffRepository {
    // 店舗IDに紐づく活動スタッフ一覧を取得する
    List<ActiveStaff> findAllByStoreId(StoreId storeId);

    // 店舗IDとスタッフIDを指定して、活動スタッフが削除可能か判定する
    boolean isRemovable(StoreId storeId, StaffId staffId);
}
