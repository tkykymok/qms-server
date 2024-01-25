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
        String serviceStartTime, // 対応開始時間
        String serviceEndTime, // 対応終了時間
        String holdStartTime, // 保留開始時間
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
        // 順番
        Integer position,
        // 案内開始時間目安
        String estimatedServiceStartTime,
        // メッセージ
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
