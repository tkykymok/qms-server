package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record ReservationMenuKey(
        ReservationId reservationId,
        StoreMenuId storeMenuId
) implements Serializable {
    public static ReservationMenuKey of(ReservationId reservationId, StoreMenuId storeMenuId) {
        return new ReservationMenuKey(reservationId, storeMenuId);
    }
}
