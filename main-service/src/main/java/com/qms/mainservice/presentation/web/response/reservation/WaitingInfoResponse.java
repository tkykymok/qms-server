package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

@Builder
public record WaitingInfoResponse(
        // 待ち人数
        Integer waitingCount,
        // 順番
        Integer position,
        // 案内開始時間目安
        String estimatedServiceStartTime
) {
}
