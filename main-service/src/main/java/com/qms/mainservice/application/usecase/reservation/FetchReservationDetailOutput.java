package com.qms.mainservice.application.usecase.reservation;

import com.qms.shared.application.usecase.BaseOutput;
import lombok.Builder;

@Builder
public record FetchReservationDetailOutput(
        ReservationOutput reservation, // 予約情報
        WaitingInfoOutput waitingInfo // 待ち情報
) implements BaseOutput {
}
