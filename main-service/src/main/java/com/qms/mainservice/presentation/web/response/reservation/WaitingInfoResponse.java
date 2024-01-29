package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

@Builder
public record WaitingInfoResponse(
        Integer waitingCount, // 待ち人数
        Integer position, // 順番
        String estimatedServiceStartTime // 案内開始時間目安
) {
}
