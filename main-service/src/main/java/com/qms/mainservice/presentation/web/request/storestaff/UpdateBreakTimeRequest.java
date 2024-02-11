package com.qms.mainservice.presentation.web.request.storestaff;


import com.qms.mainservice.application.usecase.storestaff.UpdateBreakTimeInput;
import com.qms.mainservice.domain.model.valueobject.*;

import java.time.LocalTime;

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
                .breakStartTime(BreakStartTime.of(LocalTime.parse(breakStartTime)))
                .breakEndTime(BreakEndTime.of(LocalTime.parse(breakEndTime)))
                .build();
    }
}
