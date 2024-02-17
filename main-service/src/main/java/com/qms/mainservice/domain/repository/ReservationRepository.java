package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;

import java.util.List;

public interface ReservationRepository {
    Reservation findById(ReservationId reservationId);

    Reservation findByCustomerId(CustomerId customerId);

    List<Reservation> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate);

    ReservationNumber newReservationNumber(StoreId storeId);

    // 予約を更新する
    void update(Reservation reservation);

    // 予約メニューを更新する(DELETE -> INSERT)
    void updateReservationMenus(Reservation reservation);
}
