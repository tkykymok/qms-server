package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

@Getter
public class ActiveStaff extends CompositeKeyBaseEntity<ActiveStaffKey> {

    SortOrder sortOrder;
    BreakStartDateTime breakStartDateTime;
    BreakEndDateTime breakEndDateTime;
    ReservationId reservationId;

}
