package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetReservationDetailResponse(
        Long reservationId, // 予約ID
        Long storeId, // 店舗ID
        Long customerId, // 顧客ID
        Integer reservationNumber, // 予約番号
        String reservedDate, // 予約日
        Long staffId, // 対応スタッフID
        String serviceStartDateTime, // 対応開始日時
        String serviceEndDateTime, // 対応終了日時
        String holdStartDateTime, // 保留開始日時
        Integer status, // 予約ステータス
        Boolean notified, // 通知フラグ
        Boolean arrived, // 到着フラグ
        Integer version, // バージョン
        // 店舗情報
        String storeName, // 店舗名
        String homePageUrl, // ホームページURL
        // 予約メニュー
        String menuName, // メニュー名
        BigDecimal price, // 価格
        Integer time, // 所要時間
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
