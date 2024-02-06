package com.qms.mainservice.application.usecase.reservation;

import lombok.Builder;

@Builder
public record FetchLastWaitTimeOutput(
        WaitingInfoOutput waitingInfo // 待ち情報
) {
}
