package com.qms.mainservice.application.usecase.reservation;

import com.qms.shared.application.usecase.BaseOutput;
import lombok.Builder;

import java.util.List;

@Builder
public record FetchReservationsOutput(
        List<ReservationOutput> reservations
) implements BaseOutput {
}
