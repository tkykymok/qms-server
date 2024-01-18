package com.qms.mainservice.domain.model.reservation;

import com.qms.mainservice.domain.model.staff.ActiveStaff;
import com.qms.mainservice.domain.model.valueobject.StoreId;

import java.util.List;

public class ReservationListAggregate {
    private StoreId storeId;
    private List<ActiveStaff> activeStaffs;
    private List<ReservationAggregate> reservations;
}
