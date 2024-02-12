package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.ReservationOverview;
import com.qms.mainservice.domain.model.valueobject.Position;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;

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
                .tagColor(reservation.getTagColor()) // タグ色
                // 店舗情報
                .store(reservation.getStore())
                // 顧客情報
                .customer(reservation.getCustomer()) // 顧客情報
                .build();
    }

    public static WaitingInfoOutput modelToWaitingInfoOutput(
            ReservationOverview reservationOverview,
            Reservation reservation,
            ReservationNumber reservationNumber
    ) {
        Position position = reservationOverview.getPosition(reservation);

        return WaitingInfoOutput.builder()
                .waitingCount(reservationOverview.getWaitingCount()) // 待ち人数
                .position(position) // 順番
                .reservationNumber(reservation == null
                        ? reservationNumber // 最後尾の予約番号
                        : reservation.getReservationNumber() // 自分の予約番号
                ) // 予約番号
                .activeStaffCount(reservationOverview.getActiveStaffCount()) // 活動中スタッフ数
                .waitTime(reservationOverview.calcWaitTime(position)) // 最後尾の待ち時間
                .estimatedServiceStartTime(reservationOverview.getEstimatedServiceStartTime(reservation)) // 案内開始時間目安
                .build();
    }

}
