package com.qms.mainservice.application.usecase.reservation;

import lombok.Builder;

@Builder
public record FetchReservationDetailOutput(
        ReservationOutput reservation
) {
}
