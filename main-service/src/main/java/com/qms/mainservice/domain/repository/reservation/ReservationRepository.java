package com.qms.mainservice.domain.repository.reservation;

import com.qms.mainservice.domain.model.reservation.ReservationAggregate;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {
    ReservationAggregate findById(ReservationId id);

    List<ReservationAggregate> findByStoreIdAndReservedDate(StoreId storeId, LocalDate reservedDate);

    ReservationNumber newReservationNumber();
}
