package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

import java.util.List;

@Builder
public record GetReservationsResponse(
        List<ReservationResponse> reservations,
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null ? message.message() : "";
    }
}
