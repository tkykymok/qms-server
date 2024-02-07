package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.valueobject.Flag;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.Builder;

@Builder
public record CreateOrRemoveActiveStaffInput(
        StoreId storeId,
        StaffId staffId,
        Flag isActive // 変更後の活動フラグ
) {
}
