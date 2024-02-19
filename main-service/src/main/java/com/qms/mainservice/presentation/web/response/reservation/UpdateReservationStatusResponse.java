package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import lombok.Builder;

@Builder
public record UpdateReservationStatusResponse(
        ReservationResponse reservation // 予約情報
) implements BaseResponse {
    @Override
    public String getMessage() {
        return "";
    }
}
