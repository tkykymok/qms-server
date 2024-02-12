package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

import java.util.List;

@Builder
public record ReservationResponse(
        Long reservationId, // 予約ID
        Long storeId, // 店舗ID
        Long customerId, // 顧客ID
        Integer reservationNumber, // 予約番号
        String reservedDate, // 予約日
        Long staffId, // 対応スタッフID
        String serviceStartTime, // 対応開始時間
        String serviceEndTime, // 対応終了時間
        String holdStartTime, // 保留開始時間
        Integer status, // 予約ステータス
        Boolean notified, // 通知フラグ
        Boolean arrived, // 到着フラグ
        Integer version, // バージョン
        // 予約メニュー
        List<ReservationMenuResponse> reservationMenus,
        // 店舗情報
        String storeName, // 店舗名
        String homePageUrl, // ホームページURL
        // 顧客情報
        String customerLastName, // 顧客姓
        String customerFirstName // 顧客名
) {
}