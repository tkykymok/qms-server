package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.ReservationOverview;
import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.model.valueobject.Time;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.domain.repository.StoreRepository;
import com.qms.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchLastWaitTimeUsecase extends Usecase<StoreId, FetchLastWaitTimeOutput> {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ActiveStaffRepository activeStaffRepository;

    @Override
    public FetchLastWaitTimeOutput execute(StoreId storeId) {
        // 店舗情報を取得する
        Store store = storeRepository.findById(storeId);
        // 店舗に紐づく活動スタッフを取得する
        List<ActiveStaff> activeStaffs = activeStaffRepository.findAllByStoreId(storeId);
        // 店舗に紐づく予約一覧を取得する
        List<Reservation> reservations = reservationRepository
                .findAllByStoreIdAndReservedDate(storeId, ReservedDate.now());

        // 予約状況集約を生成する
        ReservationOverview reservationOverview = ReservationOverview
                .reconstruct(store, activeStaffs, reservations);

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
