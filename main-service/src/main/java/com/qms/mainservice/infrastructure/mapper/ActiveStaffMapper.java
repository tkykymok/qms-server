package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;

import static com.qms.mainservice.infrastructure.jooq.Tables.ACTIVE_STAFFS;

public class ActiveStaffMapper {

    // RecordからActiveStaffを生成する
    public static  ActiveStaff recordToActiveStaff(Record record) {
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

}
