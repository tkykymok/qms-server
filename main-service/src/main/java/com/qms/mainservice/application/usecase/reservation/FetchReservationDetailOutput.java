package com.qms.mainservice.application.usecase.reservation;

import com.qms.mainservice.domain.model.valueobject.Count;
import com.qms.mainservice.domain.model.valueobject.Position;
import com.qms.mainservice.domain.model.valueobject.ReservationNumber;
import com.qms.mainservice.domain.model.valueobject.ServiceStartTime;
import lombok.Builder;

@Builder
public record FetchReservationDetailOutput(
        ReservationOutput reservation, // 予約情報
        Count waitingCount, // 待ち人数
        Position position, // 順番
        ReservationNumber reservationNumber, // 予約番号
        ServiceStartTime estimatedServiceStartTime // 案内開始時間目安
) {
}
