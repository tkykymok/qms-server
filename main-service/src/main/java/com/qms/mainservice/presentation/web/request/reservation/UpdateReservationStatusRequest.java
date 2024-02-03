package com.qms.mainservice.presentation.web.request.reservation;

import com.qms.mainservice.application.usecase.reservation.UpdateReservationStatusInput;
import com.qms.mainservice.domain.model.valueobject.*;

public record UpdateReservationStatusRequest(
        Long reservationId,
        Long staffId,
        int status,
        Integer version
) {
    // inputクラスに変換
    public UpdateReservationStatusInput toInput(StoreId storeId) {
        return UpdateReservationStatusInput.builder()
                .storeId(storeId)
                .reservationId(ReservationId.of(reservationId))
                .staffId(StaffId.of(staffId))
                .status(ReservationStatus.fromValue(status))
                .version(VersionKey.of(version))
                .build();
    }
}
