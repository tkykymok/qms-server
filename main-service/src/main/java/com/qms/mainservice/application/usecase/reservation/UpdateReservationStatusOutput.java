package com.qms.mainservice.application.usecase.reservation;

import com.qms.shared.application.usecase.BaseOutput;
import lombok.Builder;

@Builder
public record UpdateReservationStatusOutput(
        ReservationOutput reservation // 更新後の予約情報
) implements BaseOutput {
}
