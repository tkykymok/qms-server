package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

@Builder
public record GetLastWaitingInfoResponse(
        WaitingInfoResponse waitingInfo // 待ち情報
) {
}
