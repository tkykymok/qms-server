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
        Integer status, // 予約ステータス
        Boolean arrived, // 到着フラグ
        Integer version, // バージョン
        // 店舗情報
        String storeName, // 店舗名
        String homePageUrl, // ホームページURL
        // 予約メニュー
        String menuName, // メニュー名
        BigDecimal price, // 価格
        // 待ち人数
        Integer waitingCount,
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
