package com.qms.mainservice.application.usecase.reservation;

import com.qms.shared.application.usecase.BaseOutput;
import lombok.Builder;

@Builder
public record FetchLastWaitTimeOutput(
        WaitingInfoOutput waitingInfo // 待ち情報
) implements BaseOutput {
}
