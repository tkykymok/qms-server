package com.qms.mainservice.presentation.web.request.reservation;

public record UpdateReservationStatusRequest(
        Long reservationId,
        Long staffId,
        int status,
        Integer version
) {
}
