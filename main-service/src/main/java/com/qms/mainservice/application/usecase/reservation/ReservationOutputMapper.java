package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;

public class ReservationOutputMapper {

    public static ReservationOutput modelToReservationOutput(Reservation reservation
    ) {
        return ReservationOutput.builder()
                .reservationId(reservation.getId()) // 予約ID
                .customerId(reservation.getCustomerId()) // 顧客ID
                .storeId(reservation.getStoreId()) // 店舗ID
                .reservationNumber(reservation.getReservationNumber()) // 予約番号
                .reservedDate(reservation.getReservedDate()) // 予約日
                .staffId(reservation.getStaffId()) // 対応スタッフID
                .serviceStartTime(reservation.getServiceStartTime()) // 対応開始時間
                .serviceEndTime(reservation.getServiceEndTime()) // 対応終了時間
                .holdStartTime(reservation.getHoldStartTime()) // 保留開始時間
                .status(reservation.getStatus()) // 予約ステータス
                .notified(reservation.getNotified()) // 通知フラグ
                .arrived(reservation.getArrived()) // 到着フラグ
                .version(reservation.getVersion()) // バージョン
                // 予約メニュー
                .menuName(reservation.getMenuName()) // メニュー名
                .price(reservation.getPrice()) // 価格
                .time(reservation.getTime()) // 所要時間
                // 店舗情報
                .store(reservation.getStore())
                // 顧客情報
                .customer(reservation.getCustomer()) // 顧客情報
                .build();
    }

}
