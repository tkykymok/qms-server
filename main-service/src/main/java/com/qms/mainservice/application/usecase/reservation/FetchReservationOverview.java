//package com.qms.mainservice.application.usecase.reservation;
//
//import com.qms.mainservice.domain.model.aggregate.Store;
//import com.qms.mainservice.domain.model.entity.ActiveStaff;
//import com.qms.mainservice.domain.repository.ActiveStaffRepository;
//import com.qms.mainservice.domain.repository.ReservationRepository;
//import com.qms.mainservice.domain.repository.StoreRepository;
//import com.qms.shared.application.usecase.Usecase;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class FetchReservationOverview extends Usecase<FetchReservationOverviewInput, FetchReservationOverviewOutput> {
//
//    private final StoreRepository storeRepository;
//    private final ReservationRepository reservationRepository;
//    private final ActiveStaffRepository activeStaffRepository;
//
//    @Override
//    public FetchReservationOverviewOutput execute(FetchReservationOverviewInput input) {
//
//
//        // 店舗情報を取得する
//        Store store = storeRepository.findById(input.storeId());
//        // 店舗に紐づく活動スタッフを取得する
//        List<ActiveStaff> activeStaffs = activeStaffRepository.findAllByStoreId(input.storeId());
//        //
//
//
//        return null;
//    }
//}
