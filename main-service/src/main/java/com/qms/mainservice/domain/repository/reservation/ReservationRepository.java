package com.qms.mainservice.domain.repository.reservation;

import com.qms.mainservice.domain.model.aggregate.ReservationAggregate;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.ReservedDate;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public interface ReservationRepository {
    ReservationAggregate findById(ReservationId id);

    List<ReservationAggregate> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate);

    ReservationNumber newReservationNumber();
}
