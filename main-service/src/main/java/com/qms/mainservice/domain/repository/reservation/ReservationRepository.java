package com.qms.mainservice.domain.repository.reservation;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public interface ReservationRepository {
    Reservation findById(ReservationId id);

    List<Reservation> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate);

    ReservationNumber newReservationNumber();
}
