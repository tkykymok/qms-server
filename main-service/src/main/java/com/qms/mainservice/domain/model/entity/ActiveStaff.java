package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActiveStaff extends CompositeKeyBaseEntity<ActiveStaffKey> {

    SortOrder sortOrder;
    BreakStartDateTime breakStartDateTime;
    BreakEndDateTime breakEndDateTime;
    ReservationId reservationId;

    private ActiveStaff() {
    }

    public static ActiveStaff create(
            StoreId storeId,
            StaffId staffId,
            SortOrder sortOrder,
            BreakStartDateTime breakStartDateTime,
            BreakEndDateTime breakEndDateTime,
            ReservationId reservationId
    ) {
        ActiveStaff activeStaff = new ActiveStaff();
        activeStaff.key = new ActiveStaffKey(storeId, staffId);
        activeStaff.sortOrder = sortOrder;
        activeStaff.breakStartDateTime = breakStartDateTime;
        activeStaff.breakEndDateTime = breakEndDateTime;
        activeStaff.reservationId = reservationId;
        return activeStaff;
    }

    public boolean isInBreakTime(Time time) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nextAvailableDateTime = currentDateTime.plusMinutes(time.value());


        return breakStartDateTime.isBefore(time) && breakEndDateTime.isAfter(time);
    }

}
