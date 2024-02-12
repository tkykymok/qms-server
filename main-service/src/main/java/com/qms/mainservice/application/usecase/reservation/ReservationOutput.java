package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.entity.Customer;
import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

import java.util.List;

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
        // 予約メニューList
        List<ReservationMenuOutput> reservationMenus,
        // 店舗情報
        Store store,
        // 顧客情報
        Customer customer
) {
}
