package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.ReservationOverview;
import com.qms.mainservice.domain.model.valueobject.CustomerId;
import com.qms.mainservice.domain.model.valueobject.Position;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ServiceStartTime;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.domain.service.ReservationOverviewCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchReservationDetailUsecase extends Usecase<CustomerId, FetchReservationDetailOutput> {

    private final ReservationRepository reservationRepository;
    private final ReservationOverviewCreator reservationOverviewCreator;

    public FetchReservationDetailOutput execute(CustomerId customerId) {
        // 顧客IDから予約IDを取得する
        Reservation reservation = reservationRepository.findByCustomerId(customerId);
        if (reservation == null) {
            // 予約IDが取得できない場合はnullを返却する
            return null;
        }

        // 予約IDを取得する
        ReservationId reservationId = reservation.getId();
        // 予約状況集約を生成する
        ReservationOverview reservationOverview =
                reservationOverviewCreator.create(reservation.getStoreId());
        // 予約IDから順番を取得する
        Position position = reservationOverview.getPosition(reservationId);
        // 予約IDから案内開始時間目安を取得する
        ServiceStartTime serviceStartTime =
                reservationOverview.calcEstimatedServiceStartTime(position);

        // 予約詳細を返す
        return FetchReservationDetailOutput.builder()
                .reservation(ReservationOutput.builder()
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
                        .storeName(reservation.getStore().getStoreName()) // 店舗名
                        .homePageUrl(reservation.getStore().getHomePageUrl()) // ホームページURL
                        .menuName(reservation.getMenuName()) // メニュー名
                        .price(reservation.getPrice()) // 価格
                        .time(reservation.getTime()) // 所要時間
                        .build())
                .waitingCount(reservationOverview.getWaitingCount()) // 待ち人数
                .position(position) // 順番
                .estimatedServiceStartTime(serviceStartTime) // 案内開始時間目安
                .build();
    }

}
