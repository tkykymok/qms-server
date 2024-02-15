package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;
import lombok.Getter;

@Getter
public class Sales extends AggregateRoot<SalesId> {

    StoreId storeId;
    ReservationId reservationId;
    MenuName menuName;
    SalesAmount salesAmount;


    private Sales() {
    }

    // 予約を基に売上を作成する
    public static Sales create(Reservation reservation) {
        Sales sales = new Sales();
        sales.storeId = reservation.getStoreId();
        sales.reservationId = reservation.getId();
        sales.menuName = reservation.getMenuNames();
        sales.salesAmount = reservation.getTotalPrice();
        return sales;
    }

}
