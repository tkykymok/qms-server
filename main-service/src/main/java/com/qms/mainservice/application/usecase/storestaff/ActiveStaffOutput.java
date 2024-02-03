package com.qms.mainservice.application.usecase.storestaff;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

@Builder
public record ActiveStaffOutput(
        StoreId storeId,
        StaffId staffId,
        SortOrder sortOrder,
        BreakStartTime breakStartTime,
        BreakEndTime breakEndTime,
        ReservationId reservationId,
        CompanyId companyId,
        LastName lastName,
        FirstName firstName,
        CognitoUserId cognitoUserId
) {
}
