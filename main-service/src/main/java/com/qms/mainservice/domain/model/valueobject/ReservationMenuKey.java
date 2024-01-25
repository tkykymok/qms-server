package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;

public record ReservationMenuKey(
        ReservationId reservationId,
        StoreId storeId,
        StoreMenuId storeMenuId
) implements Serializable {
    public static ReservationMenuKey of(ReservationId reservationId, StoreId storeId, StoreMenuId storeMenuId) {
        return new ReservationMenuKey(reservationId, storeId, storeMenuId);
    }
}
