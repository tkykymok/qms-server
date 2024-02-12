package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ReservationMenuResponse(
        Integer storeMenuId, // 店舗メニューID
        String menuName, // メニュー名
        BigDecimal price, // 価格
        Integer time, // 所要時間
        String tagColor // タグ色
) {
}