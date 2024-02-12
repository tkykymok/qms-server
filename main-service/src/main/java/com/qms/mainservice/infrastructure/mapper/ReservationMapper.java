package com.qms.mainservice.infrastructure.mapper;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import com.qms.shared.domain.model.valueobject.UserType;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

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
                Optional.ofNullable(record.get(RESERVATIONS.STAFF_ID))
                        .map(StaffId::of)
                        .orElse(null),
                Optional.ofNullable(record.get(RESERVATIONS.SERVICE_START_TIME))
                        .map(ServiceStartTime::of)
                        .orElse(null),
                Optional.ofNullable(record.get(RESERVATIONS.SERVICE_END_TIME))
                        .map(ServiceEndTime::of)
                        .orElse(null),
                Optional.ofNullable(record.get(RESERVATIONS.HOLD_START_TIME))
                        .map(HoldStartTime::of)
                        .orElse(null),
                ReservationStatus.fromValue(record.get(RESERVATIONS.STATUS)),
                Flag.fromValue(record.get(RESERVATIONS.NOTIFIED).intValue()),
                Flag.fromValue(record.get(RESERVATIONS.ARRIVED).intValue()),
                VersionKey.of(record.get(RESERVATIONS.VERSION)),
                TrackingInfo.reconstruct(
                        record.get(RESERVATIONS.CREATED_AT),
                        record.get(RESERVATIONS.UPDATED_AT),
                        record.get(RESERVATIONS.CREATED_BY),
                        UserType.fromValue(record.get(RESERVATIONS.CREATED_BY_TYPE)),
                        record.get(RESERVATIONS.UPDATED_BY),
                        UserType.fromValue(record.get(RESERVATIONS.UPDATED_BY_TYPE))
                ),
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
                TrackingInfo.reconstruct(
                        record.get(RESERVATION_MENUS.CREATED_AT),
                        record.get(RESERVATION_MENUS.UPDATED_AT),
                        record.get(RESERVATION_MENUS.CREATED_BY),
                        UserType.fromValue(record.get(RESERVATION_MENUS.CREATED_BY_TYPE)),
                        record.get(RESERVATION_MENUS.UPDATED_BY),
                        UserType.fromValue(record.get(RESERVATION_MENUS.UPDATED_BY_TYPE))
                ),
                // Menuを生成
                Menu.reconstruct(
                        StoreId.of(record.get(MENUS.STORE_ID)),
                        StoreMenuId.of(record.get(MENUS.STORE_MENU_ID)),
                        MenuName.of(record.get(MENUS.MENU_NAME)),
                        Price.of(BigDecimal.valueOf(record.get(MENUS.PRICE))),
                        Time.of(record.get(MENUS.TIME)),
                        TagColor.fromValue(record.get(MENUS.TAG_COLOR)),
                        Flag.fromValue(record.get(MENUS.DISABLED).intValue()),
                        TrackingInfo.reconstruct(
                                record.get(MENUS.CREATED_AT),
                                record.get(MENUS.UPDATED_AT),
                                record.get(MENUS.CREATED_BY),
                                UserType.fromValue(record.get(MENUS.CREATED_BY_TYPE)),
                                record.get(MENUS.UPDATED_BY),
                                UserType.fromValue(record.get(MENUS.UPDATED_BY_TYPE))
                        )
                )
        );
    }

}
