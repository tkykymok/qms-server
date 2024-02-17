package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateReservationStatusInput(
        ReservationId reservationId,
        StoreId storeId,
        StaffId staffId,
        ReservationStatus status,
        List<StoreMenuId> storeMenuIds,
        VersionKey version
) {
}
