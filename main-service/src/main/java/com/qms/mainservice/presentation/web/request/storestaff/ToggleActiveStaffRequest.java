package com.qms.mainservice.presentation.web.request.storestaff;


import com.qms.mainservice.application.usecase.storestaff.CreateOrRemoveActiveStaffInput;
import com.qms.mainservice.domain.model.valueobject.Flag;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;

public record ToggleActiveStaffRequest(
        Long staffId,
        Boolean isActive
) {
    // inputクラスに変換
    public CreateOrRemoveActiveStaffInput toInput(StoreId storeId) {
        return CreateOrRemoveActiveStaffInput.builder()
                .storeId(storeId)
                .staffId(StaffId.of(staffId))
                .isActive(Flag.of(isActive))
                .build();
    }
}
