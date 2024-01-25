package com.qms.mainservice.presentation.web.response.reservation;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

@Builder
public record GetLastWaitTimeResponse(
        Integer lastWaitTime, // 最後尾の待ち時間
        Integer reservationNumber, // 予約番号
        Message message // メッセージ
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
