package com.qms.mainservice.application.usecase.reservation;

import lombok.Builder;

@Builder
public record UpdateReservationStatusOutput(
        ReservationOutput reservation // 更新後の予約情報
) {
}
