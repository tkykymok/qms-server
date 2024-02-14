package com.qms.mainservice.domain.service;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.shared.domain.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesCreator implements DomainService {


    // 該当の予約から売上を作成する
    public void createSales(Reservation reservation) {

    }

}
