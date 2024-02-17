package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.infrastructure.jooq.tables.records.ReservationMenusRecord;

public class ReservationMenuMapper {

    // ReservationMenuをReservationMenusRecordに変換する
    public static ReservationMenusRecord reservationMenuToRecord(ReservationMenu reservationMenu) {
        return new ReservationMenusRecord(
                reservationMenu.getKey().reservationId().value(),
                reservationMenu.getKey().storeId().value(),
                reservationMenu.getKey().storeMenuId().value(),
                reservationMenu.getTrackingInfo().getCreatedAt(),
                reservationMenu.getTrackingInfo().getUpdatedAt(),
                reservationMenu.getTrackingInfo().getCreatedBy(),
                reservationMenu.getTrackingInfo().getCreatedByType().getValue(),
                reservationMenu.getTrackingInfo().getUpdatedBy(),
                reservationMenu.getTrackingInfo().getUpdatedByType().getValue());
    }
}
