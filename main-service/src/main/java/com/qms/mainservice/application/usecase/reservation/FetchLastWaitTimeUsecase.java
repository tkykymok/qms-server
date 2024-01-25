package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.ReservationOverview;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.Time;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.domain.service.ReservationOverviewCreator;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchLastWaitTimeUsecase extends Usecase<StoreId, FetchLastWaitTimeOutput> {

    private final ReservationRepository reservationRepository;
    private final ReservationOverviewCreator reservationOverviewCreator;

    @Override
    public FetchLastWaitTimeOutput execute(StoreId storeId) {
        // 予約状況集約を生成する
        ReservationOverview reservationOverview =
                reservationOverviewCreator.create(storeId);

        // 最後尾の待ち時間を算出する
        Time lastWaitTime = reservationOverview.calcLastWaitTime();
        // 次の予約番号を取得する
        ReservationNumber reservationNumber = reservationRepository.newReservationNumber(storeId);

        // 最後尾の待ち時間と次の予約番号を返却する
        return FetchLastWaitTimeOutput.builder()
                .lastWaitTime(lastWaitTime)
                .reservationNumber(reservationNumber)
                .build();
    }
}
