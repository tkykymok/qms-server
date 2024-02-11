package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

@Builder
public record StoreStaffOutput(
        StaffId staffId,
        StoreId storeId,
        CompanyId companyId,
        LastName lastName,
        FirstName firstName,
        ImageUrl imageUrl,
        CognitoUserId cognitoUserId,
        Flag isActive,
        SortOrder sortOrder
) {
}
