package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

@Builder
public record UpdateReservationStatusResponse(
        ReservationResponse reservation // 予約情報
) {
}
