package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public interface StoreMenuRepository {
    // 店舗IDに紐づく店舗メニュー一覧を取得する
    List<Menu> findAllByStoreId(StoreId storeId);
}
