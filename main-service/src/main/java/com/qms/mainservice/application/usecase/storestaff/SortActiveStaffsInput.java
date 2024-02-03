package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Builder;

import java.util.List;

@Builder
public record SortActiveStaffsInput(
        StoreId storeId,
        List<StaffId> staffIds
) {
}
