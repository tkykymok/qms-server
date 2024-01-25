package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.ReservationOverview;
import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.domain.repository.ActiveStaffRepository;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationOverviewCreator implements DomainService {

    private final ReservationRepository reservationRepository;
    private final ActiveStaffRepository activeStaffRepository;

    // 店舗IDを指定して予約状況集約を生成する
    public ReservationOverview create(StoreId storeId) {
        // 店舗に紐づく活動スタッフを取得する
        List<ActiveStaff> activeStaffs = activeStaffRepository.findAllByStoreId(storeId);
        // 店舗に紐づく予約一覧を取得する
        List<Reservation> reservations = reservationRepository
                .findAllByStoreIdAndReservedDate(storeId, ReservedDate.now());

        // 予約状況集約を生成する
        return ReservationOverview
                .reconstruct(storeId, activeStaffs, reservations);
    }
}
