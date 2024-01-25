package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.CustomerId;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchReservationDetailUsecase extends Usecase<CustomerId, FetchReservationDetailOutput> {

    private final ReservationRepository reservationRepository;

    public FetchReservationDetailOutput execute(CustomerId customerId) {
        // 顧客IDから予約IDを取得する
        ReservationId reservationId = reservationRepository.findIdByCustomerId(customerId);
        if (reservationId == null) {
            // 予約IDが取得できない場合はnullを返却する
            return null;
        }

        // 予約IDから予約詳細を取得する
        Reservation reservation = reservationRepository.findById(reservationId);

        // 予約詳細を返す
        return FetchReservationDetailOutput.builder()
                .reservation(ReservationOutput.builder()
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
                        .storeName(reservation.getStore().getStoreName()) // 店舗名
                        .homePageUrl(reservation.getStore().getHomePageUrl()) // ホームページURL
                        .menuName(reservation.getMenuName()) // メニュー名
                        .price(reservation.getPrice()) // 価格
                        .time(reservation.getTime()) // 所要時間
                        .build())
                .build();
    }

}
