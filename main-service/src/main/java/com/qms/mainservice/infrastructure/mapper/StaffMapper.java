package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.Staff;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;

import static com.qms.mainservice.infrastructure.jooq.Tables.STAFFS;

public class StaffMapper {

    public static Staff recordToStaff(Record record) {
        return Staff.reconstruct(
                StaffId.of(record.get(STAFFS.ID)),
                CompanyId.of(record.get(STAFFS.COMPANY_ID)),
                LastName.of(record.get(STAFFS.LAST_NAME)),
                FirstName.of(record.get(STAFFS.FIRST_NAME)),
                CognitoUserId.of(record.get(STAFFS.COGNITO_USER_ID))
        );
    }
}
