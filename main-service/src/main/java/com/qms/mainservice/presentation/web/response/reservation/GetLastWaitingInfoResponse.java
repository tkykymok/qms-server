package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import lombok.Builder;

@Builder
public record GetLastWaitingInfoResponse(
        WaitingInfoResponse waitingInfo // 待ち情報
) implements BaseResponse {
    @Override
    public String getMessage() {
        return "";
    }
}
