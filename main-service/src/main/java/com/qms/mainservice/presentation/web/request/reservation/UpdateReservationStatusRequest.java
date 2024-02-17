package com.qms.mainservice.presentation.web.request.reservation;

import com.qms.mainservice.application.usecase.reservation.UpdateReservationStatusInput;
import com.qms.mainservice.domain.model.valueobject.*;

import java.util.List;

public record UpdateReservationStatusRequest(
        Long staffId,
        int status,
        List<Integer> storeMenuIds,
        Integer version
) {
    // inputクラスに変換
    public UpdateReservationStatusInput toInput(StoreId storeId, ReservationId reservationId) {
        return UpdateReservationStatusInput.builder()
                .storeId(storeId)
                .reservationId(reservationId)
                .staffId(StaffId.of(staffId))
                .status(ReservationStatus.fromValue(status))
                .storeMenuIds(storeMenuIds.stream()
                        .map(StoreMenuId::of)
                        .toList())
                .version(VersionKey.of(version))
                .build();
    }
}
