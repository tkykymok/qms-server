package com.qms.mainservice.application.usecase.staff;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

@Builder
public record StoreStaffOutput(
        StaffId staffId,
        StoreId storeId,
        CompanyId companyId,
        LastName lastName,
        FirstName firstName,
        CognitoUserId cognitoUserId,
        Flag isActive,
        ReservationId reservationId
) {
}
