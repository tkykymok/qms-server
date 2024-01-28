package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.Staff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

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

    // RecordからActiveStaffを生成する
    public static ActiveStaff recordToActiveStaff(Record record) {
        return ActiveStaff.reconstruct(
                StoreId.of(record.get(ACTIVE_STAFFS.STORE_ID)),
                StaffId.of(record.get(ACTIVE_STAFFS.STAFF_ID)),
                SortOrder.of(record.get(ACTIVE_STAFFS.SORT_ORDER)),
                BreakStartTime.of(record.get(ACTIVE_STAFFS.BREAK_START_TIME)),
                BreakEndTime.of(record.get(ACTIVE_STAFFS.BREAK_END_TIME)),
                ReservationId.of(record.get(ACTIVE_STAFFS.RESERVATION_ID)),
                StaffMapper.recordToStaff(record)
        );
    }

    public static StoreStaff recordToStoreStaff(Record record) {
        return StoreStaff.reconstruct(
                StaffId.of(record.get(STORE_STAFFS.STAFF_ID)),
                StoreId.of(record.get(STORE_STAFFS.STORE_ID)),
                StaffMapper.recordToStaff(record)
        );
    }
}
