package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

@Builder
public record GetLastWaitTimeResponse(
        Integer lastWaitTime,
        Integer reservationNumber,
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
