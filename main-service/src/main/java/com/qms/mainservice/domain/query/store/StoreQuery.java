package com.qms.mainservice.domain.query.store;

import java.awt.*;
import java.util.List;

public interface StoreQuery {

    // 経度、緯度、距離を指定して店舗情報を取得する
    List<StoreDetailsResult> findStoreDetailsByLocation(Point point, int distance);

}
