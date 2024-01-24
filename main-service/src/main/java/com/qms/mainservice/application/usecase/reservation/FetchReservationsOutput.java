package com.qms.mainservice.application.usecase.reservation;

import lombok.Builder;

import java.util.List;

@Builder
public record FetchReservationsOutput(
        List<ReservationOutput> reservations
) {
}
