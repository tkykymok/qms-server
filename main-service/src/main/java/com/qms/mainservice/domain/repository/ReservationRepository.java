package com.qms.mainservice.domain.repository;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.*;

import java.util.List;

public interface ReservationRepository {
    Reservation findById(ReservationId reservationId);

    Reservation findByCustomerId(CustomerId customerId);

    List<Reservation> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate);

    ReservationNumber newReservationNumber(StoreId storeId);

    void update(Reservation reservation);
}
