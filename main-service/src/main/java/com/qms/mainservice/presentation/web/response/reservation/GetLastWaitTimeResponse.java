package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import lombok.Builder;

@Builder
public record GetLastWaitTimeResponse(
        Integer lastWaitTime,
        Integer reservationNumber
) implements BaseResponse {
    @Override
    public String getMessage() {
        return "";
    }
}
