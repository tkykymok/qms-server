package com.qms.mainservice.domain.model.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;

import java.util.List;

public class ReservationAggregate extends AggregateRoot<ReservationId> {

    private CustomerId customerId;
    private StoreId storeId;
    private ReservationNumber reservationNumber;
    private ReservedDateTime reservedDateTime;
    private StaffId staffId;
    private ServiceStartDateTime serviceStartDateTime;
    private ServiceEndDateTime serviceEndDateTime;
    private HoldStartDateTime holdStartDateTime;
    private ReservationStatus status;
    private Flag notified;
    private Flag arrived;
    private VersionKey version;

    List<ReservationMenu> reservationMenus;

    private ReservationAggregate() {
        super();
    }

    public static ReservationAggregate createReservation(
            CustomerId customerId,
            StoreId storeId,
            List<StoreMenuId> storeMenuIds,
            ReservationNumber reservationNumber
    ) {
        ReservationAggregate reservationAggregate = new ReservationAggregate();
        reservationAggregate.customerId = customerId;
        reservationAggregate.storeId = storeId;
        reservationAggregate.reservationNumber = reservationNumber;
        reservationAggregate.reservedDateTime = ReservedDateTime.now();
        reservationAggregate.status = ReservationStatus.WAITING;
        reservationAggregate.notified = Flag.OFF();
        reservationAggregate.arrived = Flag.OFF();
        reservationAggregate.version = VersionKey.newVersion();
        return reservationAggregate;
    }


}
