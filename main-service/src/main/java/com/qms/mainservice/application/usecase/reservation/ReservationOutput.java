package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

@Builder
public record ReservationOutput(
        ReservationId reservationId, // 予約ID
        StoreId storeId, // 店舗ID
        CustomerId customerId, // 顧客ID
        ReservationNumber reservationNumber, // 予約番号
        ReservedDate reservedDate, // 予約日
        StaffId staffId, // 対応スタッフID
        ServiceStartTime serviceStartTime, // 対応開始時間
        ServiceEndTime serviceEndTime, // 対応終了時間
        HoldStartTime holdStartTime, // 保留開始時間
        ReservationStatus status, // 予約ステータス
        Flag notified, // 通知フラグ
        Flag arrived, // 到着フラグ
        VersionKey version, // バージョン
        // 店舗情報
        StoreName storeName, // 店舗名
        HomePageUrl homePageUrl, // ホームページURL
        // 予約メニュー
        MenuName menuName, // メニュー名
        Price price, // 価格
        Time time // 所要時間
) {
}
