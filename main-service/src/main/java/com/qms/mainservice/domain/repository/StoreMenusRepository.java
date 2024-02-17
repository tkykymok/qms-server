package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.StoreMenuId;

import java.util.Map;

public interface StoreMenusRepository {
    // 店舗IDを基にメニューMapを取得する
    Map<StoreMenuId, Menu> findAllByStoreId(StoreId storeId);
}
