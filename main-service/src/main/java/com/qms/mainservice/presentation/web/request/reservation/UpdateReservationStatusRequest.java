package com.qms.mainservice.presentation.web.request.reservation;

import com.qms.mainservice.application.usecase.reservation.UpdateReservationStatusInput;
import com.qms.mainservice.domain.model.valueobject.*;

public record UpdateReservationStatusRequest(
        Long staffId,
        int status,
        Integer version
) {
    // inputクラスに変換
    public UpdateReservationStatusInput toInput(StoreId storeId, ReservationId reservationId) {
        return UpdateReservationStatusInput.builder()
                .storeId(storeId)
                .reservationId(reservationId)
                .staffId(StaffId.of(staffId))
                .status(ReservationStatus.fromValue(status))
                .version(VersionKey.of(version))
                .build();
    }
}
