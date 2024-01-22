package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record GetReservationsResponse(
        List<ReservationResponse> reservations
) implements BaseResponse {
    @Override
    public String getMessage() {
        return "予約一覧を取得しました。";
    }
}
