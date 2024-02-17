package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.Sales;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.domain.repository.SalesRepository;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesCreator implements DomainService {

    private final ReservationRepository reservationRepository;
    private final SalesRepository salesRepository;

    // 予約IDを指定して売上を作成する
    public void create(ReservationId reservationId) {
        // 予約を取得する
        Reservation reservation = reservationRepository.findById(reservationId);
        // 予約を基に売上集約を作成する
        Sales sales = Sales.create(reservation);
        // 売上を保存する
        salesRepository.insert(sales);
    }

}
