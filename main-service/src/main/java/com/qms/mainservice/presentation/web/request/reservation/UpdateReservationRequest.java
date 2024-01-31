package com.qms.mainservice.presentation.web.request.reservation;

public record UpdateReservationRequest(
        Long reservationId,
        Long storeId,
        Long staffId,
        int status
) {
}
