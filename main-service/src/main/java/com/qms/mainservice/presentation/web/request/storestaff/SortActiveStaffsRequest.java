package com.qms.mainservice.presentation.web.request.storestaff;


import com.qms.mainservice.application.usecase.storestaff.SortActiveStaffsInput;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public record SortActiveStaffsRequest(
        List<Long> staffIds
) {
    // inputクラスに変換
    public SortActiveStaffsInput toInput(StoreId storeId) {
        return SortActiveStaffsInput.builder()
                .storeId(storeId)
                .staffIds(staffIds.stream().map(StaffId::of).toList())
                .build();
    }
}
