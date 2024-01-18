package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.MenuName;
import com.qms.mainservice.domain.model.valueobject.ReservationId;
import com.qms.mainservice.domain.model.valueobject.SalesAmount;
import com.qms.mainservice.domain.model.valueobject.SalesId;
import com.qms.shared.domain.model.SingleKeyBaseEntity;

public class Sales extends SingleKeyBaseEntity<SalesId> {

    ReservationId reservationId;
    MenuName menuName;
    SalesAmount salesAmount;

}
