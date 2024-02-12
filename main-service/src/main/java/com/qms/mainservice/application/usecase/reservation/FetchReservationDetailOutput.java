package com.qms.mainservice.application.usecase.reservation;

import lombok.Builder;

@Builder
public record FetchReservationDetailOutput(
        ReservationOutput reservation, // 予約情報
        WaitingInfoOutput waitingInfo // 待ち情報
) {
}
