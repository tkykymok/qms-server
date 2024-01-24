package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

import java.util.List;

@Builder
public record FetchReservationOverviewOutput(
        StoreId storeId, // 店舗ID
        StoreName storeName, // 店舗名

        Time waitTime, // 待ち時間
        ReservedDate reservedDate, // 予約日
        ServiceStartDateTime expectedStartFrom, // 予約開始時刻の範囲（開始）
        ServiceStartDateTime expectedStartTo, // 予約開始時刻の範囲（終了）
        ReservationOutput reservation, // 予約情報
        List<ReservationId> reservationIds // 予約ID一覧
) {
}
