package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.ReservationStatus;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Builder;

@Builder
public record UpdateReservationStatusInput(
        ReservationId reservationId,
        StoreId storeId,
        StaffId staffId,
        ReservationStatus status
) {
}
