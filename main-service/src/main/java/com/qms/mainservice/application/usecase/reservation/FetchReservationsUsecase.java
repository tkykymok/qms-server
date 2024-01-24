package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchReservationsUsecase extends Usecase<StoreId, FetchLastWaitTimeOutput> {

    private final ReservationRepository reservationRepository;

    public FetchLastWaitTimeOutput execute(StoreId input) {
        // 店舗IDから当日の予約一覧を取得する
        List<Reservation> result =
                reservationRepository.findAllByStoreIdAndReservedDate(input, ReservedDate.now());
        if (result.isEmpty()) {
            // 予約一覧が存在しない場合は空の予約一覧を返す
            return FetchLastWaitTimeOutput.builder()
                    .reservations(List.of())
                    .build();
        }

        List<ReservationOutput> reservationOutputs = result.stream()
                .map(reservation -> ReservationOutput.builder()
                        .reservationId(reservation.getId()) // 予約ID
                        .customerId(reservation.getCustomerId()) // 顧客ID
                        .storeId(reservation.getStoreId()) // 店舗ID
                        .reservationNumber(reservation.getReservationNumber()) // 予約番号
                        .reservedDate(reservation.getReservedDate()) // 予約日
                        .staffId(reservation.getStaffId()) // 対応スタッフID
                        .serviceStartDateTime(reservation.getServiceStartDateTime()) // 対応開始日時
                        .serviceEndDateTime(reservation.getServiceEndDateTime()) // 対応終了日時
                        .holdStartDateTime(reservation.getHoldStartDateTime()) // 保留開始日時
                        .status(reservation.getStatus()) // 予約ステータス
                        .notified(reservation.getNotified()) // 通知フラグ
                        .arrived(reservation.getArrived()) // 到着フラグ
                        .version(reservation.getVersion()) // バージョン
                        .menuName(reservation.getMenuName()) // メニュー名
                        .price(reservation.getPrice()) // 価格
                        .time(reservation.getTime()) // 所要時間
                        .build())
                .toList();

        // 予約一覧を返す
        return FetchLastWaitTimeOutput.builder()
                .reservations(reservationOutputs)
                .build();
    }

}
