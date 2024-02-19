package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.application.usecase.BaseOutput;
import lombok.Builder;

@Builder
public record ReservationMenuOutput(
        ReservationId reservationId, // 予約ID
        StoreId storeId, // 店舗ID
        StoreMenuId storeMenuId, // 店舗メニューID
        MenuName menuName, // メニュー名
        Price price, // 価格
        Time time, // 所要時間
        TagColor tagColor // タグ色
) implements BaseOutput {
}
