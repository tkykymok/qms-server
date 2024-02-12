package com.qms.mainservice.presentation.web.request.storestaff;


import com.qms.mainservice.application.usecase.storestaff.UpdateBreakTimeInput;
import com.qms.mainservice.domain.model.valueobject.*;

import java.time.LocalTime;
import java.util.Optional;

public record UpdateBreakTimeRequest(
        Long staffId,
        String breakStartTime,
        String breakEndTime
) {
    // inputクラスに変換
    public UpdateBreakTimeInput toInput(StoreId storeId) {
        return UpdateBreakTimeInput.builder()
                .storeId(storeId)
                .activeStaffKey(ActiveStaffKey.of(storeId, StaffId.of(staffId)))
                .breakStartTime(Optional.ofNullable(breakStartTime)
                        .map(LocalTime::parse)
                        .map(BreakStartTime::of)
                        .orElse(null))
                .breakEndTime(Optional.ofNullable(breakEndTime)
                        .map(LocalTime::parse)
                        .map(BreakEndTime::of)
                        .orElse(null))
                .build();
    }
}
