package com.qms.mainservice.presentation.web.response.reservation;

import lombok.Builder;

@Builder
public record WaitingInfoResponse(
        Integer waitingCount, // 待ち人数
        Integer position, // 順番
        Integer reservationNumber, // 予約番号
        Integer activeStaffCount, // 活動中スタッフ数
        Integer waitTime, // 待ち時間
        String estimatedServiceStartTime // 案内開始時間目安
) {
}
