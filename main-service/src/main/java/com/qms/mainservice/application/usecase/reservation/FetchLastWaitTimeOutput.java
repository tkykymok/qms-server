package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.Count;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.Time;
import lombok.Builder;

@Builder
public record FetchLastWaitTimeOutput(
        Time lastWaitTime,
        ReservationNumber reservationNumber,
        Count activeStaffCount,
        Count waitingCount
) {
}
