package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetReservationDetailResponse(
        ReservationResponse reservation, // 予約情報
        WaitingInfoResponse waitingInfo, // 待ち情報
        // メッセージ
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
