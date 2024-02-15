package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.Sales;
import com.qms.mainservice.domain.repository.SalesRepository;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesCreator implements DomainService {

    private final SalesRepository salesRepository;

    // 該当の予約から売上を作成する
    public void createSales(Reservation reservation) {
        // 予約を基に売上集約を作成する
        Sales sales = Sales.create(reservation);

        // 売上を保存する
        salesRepository.insert(sales);
    }

}
