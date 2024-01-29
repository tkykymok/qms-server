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
        // 顧客IDから対象の予約を取得する
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
                reservationOverview.getEstimatedServiceStartTime(position);

        // 予約詳細を返す
        return FetchReservationDetailOutput.builder()
                .reservation(ReservationOutputMapper.modelToReservationOutput(reservation)) // 予約情報
                .waitingCount(reservationOverview.getWaitingCount()) // 待ち人数
                .position(position) // 順番
                .reservationNumber(reservation.getReservationNumber()) // 予約番号
                .estimatedServiceStartTime(serviceStartTime) // 案内開始時間目安
                .build();
    }

}
