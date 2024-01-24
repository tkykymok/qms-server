package com.qms.mainservice.domain.repository.store;

import com.qms.mainservice.domain.model.aggregate.Store;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface StoreRepository {
    // 経度、緯度、距離を指定して店舗情報を取得する
    List<Store> findStoreDetailsByLocation(Point point, int distance);


}
