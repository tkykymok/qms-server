package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.util.Map;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;
import static com.qms.mainservice.infrastructure.jooq.Tables.MENUS;


public class ReservationMapper {

    public static Reservation recordToReservation(Record record, Map<Long, Result<Record>> reservationMenusMap) {
        return Reservation.reconstruct(
                ReservationId.of(record.get(RESERVATIONS.ID)),
                CustomerId.of(record.get(RESERVATIONS.CUSTOMER_ID)),
                StoreId.of(record.get(RESERVATIONS.STORE_ID)),
                ReservationNumber.of(record.get(RESERVATIONS.RESERVATION_NUMBER)),
                ReservedDate.of(record.get(RESERVATIONS.RESERVED_DATE)),
                StaffId.of(record.get(RESERVATIONS.STAFF_ID)),
                ServiceStartTime.of(record.get(RESERVATIONS.SERVICE_START_TIME)),
                ServiceEndTime.of(record.get(RESERVATIONS.SERVICE_END_TIME)),
                HoldStartTime.of(record.get(RESERVATIONS.HOLD_START_TIME)),
                ReservationStatus.fromValue(record.get(RESERVATIONS.STATUS)),
                Flag.fromValue(record.get(RESERVATIONS.NOTIFIED).intValue()),
                Flag.fromValue(record.get(RESERVATIONS.ARRIVED).intValue()),
                VersionKey.of(record.get(RESERVATIONS.VERSION)),
                // 店舗情報
                StoreMapper.recordToStore(record, Map.of()),
                reservationMenusMap.isEmpty()
                        ? null
                        : reservationMenusMap.get(record.get(RESERVATIONS.ID))
                        .map(ReservationMapper::recordsToReservationMenu),
                // 顧客情報
                CustomerMapper.recordToCustomer(record)

        );
    }

    public static ReservationMenu recordsToReservationMenu(Record record) {
        return ReservationMenu.reconstruct(
                ReservationId.of(record.get(RESERVATION_MENUS.RESERVATION_ID)),
                StoreId.of(record.get(RESERVATION_MENUS.STORE_ID)),
                StoreMenuId.of(record.get(RESERVATION_MENUS.STORE_MENU_ID)),
                // Menuを生成
                Menu.reconstruct(
                        StoreId.of(record.get(MENUS.STORE_ID)),
                        StoreMenuId.of(record.get(MENUS.STORE_MENU_ID)),
                        MenuName.of(record.get(MENUS.MENU_NAME)),
                        Price.of(BigDecimal.valueOf(record.get(MENUS.PRICE))),
                        Time.of(record.get(MENUS.TIME)),
                        Flag.fromValue(record.get(MENUS.DISABLED).intValue())
                )
        );
    }

}
