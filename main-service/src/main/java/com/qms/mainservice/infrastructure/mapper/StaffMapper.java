package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.ActiveStaff;
import com.qms.mainservice.domain.model.entity.Staff;
import com.qms.mainservice.domain.model.entity.StoreStaff;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.infrastructure.jooq.tables.records.ActiveStaffsRecord;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import com.qms.shared.domain.model.valueobject.UserType;
import org.jooq.Record;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

public class StaffMapper {

    public static Staff recordToStaff(Record record) {
        return Staff.reconstruct(
                StaffId.of(record.get(STAFFS.ID)),
                CompanyId.of(record.get(STAFFS.COMPANY_ID)),
                LastName.of(record.get(STAFFS.LAST_NAME)),
                FirstName.of(record.get(STAFFS.FIRST_NAME)),
                CognitoUserId.of(record.get(STAFFS.COGNITO_USER_ID)),
                TrackingInfo.reconstruct(
                        record.get(STAFFS.CREATED_AT),
                        record.get(STAFFS.UPDATED_AT),
                        record.get(STAFFS.CREATED_BY),
                        UserType.fromValue(record.get(STAFFS.CREATED_BY_TYPE)),
                        record.get(STAFFS.UPDATED_BY),
                        UserType.fromValue(record.get(STAFFS.UPDATED_BY_TYPE))
                )
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
                TrackingInfo.reconstruct(
                        record.get(ACTIVE_STAFFS.CREATED_AT),
                        record.get(ACTIVE_STAFFS.UPDATED_AT),
                        record.get(ACTIVE_STAFFS.CREATED_BY),
                        UserType.fromValue(record.get(ACTIVE_STAFFS.CREATED_BY_TYPE)),
                        record.get(ACTIVE_STAFFS.UPDATED_BY),
                        UserType.fromValue(record.get(ACTIVE_STAFFS.UPDATED_BY_TYPE))
                ),
                StaffMapper.recordToStaff(record)
        );
    }

    public static StoreStaff recordToStoreStaff(Record record) {
        return StoreStaff.reconstruct(
                StaffId.of(record.get(STORE_STAFFS.STAFF_ID)),
                StoreId.of(record.get(STORE_STAFFS.STORE_ID)),
                TrackingInfo.reconstruct(
                        record.get(STORE_STAFFS.CREATED_AT),
                        record.get(STORE_STAFFS.UPDATED_AT),
                        record.get(STORE_STAFFS.CREATED_BY),
                        UserType.fromValue(record.get(STORE_STAFFS.CREATED_BY_TYPE)),
                        record.get(STORE_STAFFS.UPDATED_BY),
                        UserType.fromValue(record.get(STORE_STAFFS.UPDATED_BY_TYPE))
                ),
                StaffMapper.recordToStaff(record)
        );
    }

    // ActiveStaffをActiveStaffsRecordに変換する
    public static ActiveStaffsRecord activeStaffToRecord(ActiveStaff activeStaff) {
        return new ActiveStaffsRecord(
                activeStaff.getKey().storeId().value(),
                activeStaff.getKey().staffId().value(),
                activeStaff.getSortOrder().value(),
                activeStaff.getBreakStartTime().value(),
                activeStaff.getBreakEndTime().value(),
                activeStaff.getReservationId().value(),
                activeStaff.getTrackingInfo().getCreatedAt(),
                activeStaff.getTrackingInfo().getUpdatedAt(),
                activeStaff.getTrackingInfo().getCreatedBy(),
                activeStaff.getTrackingInfo().getCreatedByType().getValue(),
                activeStaff.getTrackingInfo().getUpdatedBy(),
                activeStaff.getTrackingInfo().getUpdatedByType().getValue()
        );
    }
}
