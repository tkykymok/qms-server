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
        ServiceStartDateTime serviceStartDateTime, // 対応開始日時
        ServiceEndDateTime serviceEndDateTime, // 対応終了日時
        HoldStartDateTime holdStartDateTime, // 保留開始日時
        ReservationStatus status, // 予約ステータス
        Flag notified, // 通知フラグ
        Flag arrived, // 到着フラグ
        VersionKey version // バージョン
) {
}