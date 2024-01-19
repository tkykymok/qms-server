package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.ReservationAggregate;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.reservation.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FetchReservationsUsecase extends Usecase<StoreId, FetchReservationsOutput> {

    private final ReservationRepository reservationRepository;

    public FetchReservationsOutput execute(StoreId input) {
        // 店舗IDから当日の予約一覧を取得する
        List<ReservationAggregate> result =
                reservationRepository.findAllByStoreIdAndReservedDate(input, ReservedDate.now());

        List<ReservationOutput> reservationOutputs = result.stream()
                .map(reservationAggregate -> ReservationOutput.builder()
                        .reservationId(reservationAggregate.getId()) // 予約ID
                        .customerId(reservationAggregate.getCustomerId()) // 顧客ID
                        .storeId(reservationAggregate.getStoreId()) // 店舗ID
                        .reservationNumber(reservationAggregate.getReservationNumber()) // 予約番号
                        .reservedDate(reservationAggregate.getReservedDate()) // 予約日
                        .staffId(reservationAggregate.getStaffId()) // 対応スタッフID
                        .serviceStartDateTime(reservationAggregate.getServiceStartDateTime()) // 対応開始日時
                        .serviceEndDateTime(reservationAggregate.getServiceEndDateTime()) // 対応終了日時
                        .holdStartDateTime(reservationAggregate.getHoldStartDateTime()) // 保留開始日時
                        .status(reservationAggregate.getStatus()) // 予約ステータス
                        .notified(reservationAggregate.getNotified()) // 通知フラグ
                        .arrived(reservationAggregate.getArrived()) // 到着フラグ
                        .version(reservationAggregate.getVersion()) // バージョン
                        .build())
                .toList();

        // 予約一覧を返す
        return FetchReservationsOutput.builder()
                .reservations(reservationOutputs)
                .build();
    }

}
