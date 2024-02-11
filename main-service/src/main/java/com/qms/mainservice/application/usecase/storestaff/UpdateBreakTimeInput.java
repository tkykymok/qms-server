package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.valueobject.ActiveStaffKey;
import com.qms.mainservice.domain.model.valueobject.BreakEndTime;
import com.qms.mainservice.domain.model.valueobject.BreakStartTime;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Builder;

@Builder
public record UpdateBreakTimeInput(
        StoreId storeId,
        ActiveStaffKey activeStaffKey,
        BreakStartTime breakStartTime,
        BreakEndTime breakEndTime
) {
}
