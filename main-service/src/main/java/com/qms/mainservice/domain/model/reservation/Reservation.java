package com.qms.mainservice.domain.model.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.SingleKeyBaseEntity;

public class Reservation extends SingleKeyBaseEntity<ReservationId> {
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
}
