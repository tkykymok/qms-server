package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

@Builder
public record WaitingInfoOutput(
        Count waitingCount, // 待ち人数
        Position position, // 順番
        ReservationNumber reservationNumber, // 予約番号
        Count activeStaffCount, // 活動中スタッフ数
        Time waitTime, // 待ち時間
        ServiceStartTime estimatedServiceStartTime // 案内開始時間目安*
) {
}