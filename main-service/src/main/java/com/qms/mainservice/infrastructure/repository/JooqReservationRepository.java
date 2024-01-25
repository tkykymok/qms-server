package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.aggregate.Store;
import com.qms.mainservice.domain.model.entity.Menu;
import com.qms.mainservice.domain.model.entity.ReservationMenu;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.qms.mainservice.infrastructure.jooq.Tables.*;

@Repository
@RequiredArgsConstructor
public class JooqReservationRepository implements ReservationRepository {

    private final DSLContext dsl;

    @Override
    public Reservation findById(ReservationId reservationId) {
        // 予約IDに紐づく予約(店舗情報含む)を取得する
        Record reservationRecord = dsl.select()
                .from(RESERVATIONS)
                .innerJoin(STORES).on(RESERVATIONS.STORE_ID.eq(STORES.ID))
                .where(RESERVATIONS.ID.eq(reservationId.value()))
                .fetchOne();
        // 予約IDが取得できない場合はnullを返却する
        if (reservationRecord == null) {
            return null;
        }

        // 予約IDに紐づく予約メニュー一覧を取得する
        Map<Long, Result<Record>> reservationMenuMap = dsl.select()
                .from(RESERVATION_MENUS)
                .innerJoin(MENUS).on(RESERVATION_MENUS.STORE_ID.eq(MENUS.STORE_ID)
                        .and(RESERVATION_MENUS.STORE_MENU_ID.eq(MENUS.STORE_MENU_ID)))
                .where(RESERVATION_MENUS.RESERVATION_ID.eq(reservationId.value()))
                .fetch()
                .intoGroups(RESERVATION_MENUS.RESERVATION_ID);

        return recordToReservation(reservationRecord, reservationMenuMap);
    }

    @Override
    public Reservation findByCustomerId(CustomerId customerId) {
        // 顧客IDに紐づく予約を取得する
        Record reservationRecord = dsl.select()
                .from(RESERVATIONS)
                .innerJoin(STORES).on(RESERVATIONS.STORE_ID.eq(STORES.ID))
                .where(RESERVATIONS.CUSTOMER_ID.eq(customerId.value()))
                .fetchOne();
        // 予約IDが取得できない場合はnullを返却する
        if (reservationRecord == null) {
            return null;
        }

        // 予約IDに紐づく予約メニュー一覧を取得する
        Map<Long, Result<Record>> reservationMenuMap = dsl.select()
                .from(RESERVATION_MENUS)
                .innerJoin(MENUS).on(RESERVATION_MENUS.STORE_ID.eq(MENUS.STORE_ID)
                        .and(RESERVATION_MENUS.STORE_MENU_ID.eq(MENUS.STORE_MENU_ID)))
                .where(RESERVATION_MENUS.RESERVATION_ID.eq(reservationRecord.get(RESERVATIONS.ID)))
                .fetch()
                .intoGroups(RESERVATION_MENUS.RESERVATION_ID);

        return recordToReservation(reservationRecord, reservationMenuMap);
    }

    @Override
    public List<Reservation> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate) {
        // 予約一覧を取得する
        Result<Record> reservationRecords = dsl.select()
                .from(RESERVATIONS)
                .innerJoin(STORES).on(RESERVATIONS.STORE_ID.eq(STORES.ID))
                .where(RESERVATIONS.STORE_ID.eq(storeId.value()))
                .and(RESERVATIONS.RESERVED_DATE.eq(reservedDate.value()))
                .fetch();

        // 店舗 & ReservedDateに紐づく予約メニュー一覧を取得し、予約IDをキーにMapに格納する
        Map<Long, Result<Record>> reservationMenuMap = dsl.select()
                .from(RESERVATION_MENUS)
                .innerJoin(RESERVATIONS).on(RESERVATION_MENUS.RESERVATION_ID.eq(RESERVATIONS.ID))
                .innerJoin(MENUS).on(RESERVATION_MENUS.STORE_ID.eq(MENUS.STORE_ID)
                        .and(RESERVATION_MENUS.STORE_MENU_ID.eq(MENUS.STORE_MENU_ID)))
                .where(RESERVATIONS.STORE_ID.eq(storeId.value()).
                        and(RESERVATIONS.RESERVED_DATE.eq(reservedDate.value())))
                .fetch()
                .intoGroups(RESERVATION_MENUS.RESERVATION_ID);

        return reservationRecords
                .map(record -> recordToReservation(record, reservationMenuMap));
    }

    @Override
    public ReservationNumber newReservationNumber(StoreId storeId) {
        // 店舗ごとの次の予約番号を取得する
        Record1<Integer> maxReservationNumber = dsl
                .select(DSL.max(RESERVATIONS.RESERVATION_NUMBER))
                .from(RESERVATIONS)
                .where(RESERVATIONS.STORE_ID.eq(storeId.value())
                        .and(RESERVATIONS.RESERVED_DATE.eq(ReservedDate.now().value()))
                )
                .fetchOne();

        int nextReservationNumber = 1;
        if (maxReservationNumber != null && maxReservationNumber.value1() != null) {
            nextReservationNumber = maxReservationNumber.value1() + 1;
        }

        return ReservationNumber.of(nextReservationNumber);
    }

    // Record から Reservationを生成
    private Reservation recordToReservation(Record record, Map<Long, Result<Record>> reservationMenusMap) {
        return Reservation.reconstruct(
                ReservationId.of(record.get(RESERVATIONS.ID)),
                CustomerId.of(record.get(RESERVATIONS.CUSTOMER_ID)),
                StoreId.of(record.get(RESERVATIONS.STORE_ID)),
                ReservationNumber.of(record.get(RESERVATIONS.RESERVATION_NUMBER)),
                ReservedDate.of(record.get(RESERVATIONS.RESERVED_DATE)),
                StaffId.of(record.get(RESERVATIONS.STAFF_ID)),
                ServiceStartDateTime.of(record.get(RESERVATIONS.SERVICE_START_DATETIME)),
                ServiceEndDateTime.of(record.get(RESERVATIONS.SERVICE_END_DATETIME)),
                HoldStartDateTime.of(record.get(RESERVATIONS.HOLD_START_DATETIME)),
                ReservationStatus.fromValue(record.get(RESERVATIONS.STATUS)),
                Flag.fromValue(record.get(RESERVATIONS.NOTIFIED).intValue()),
                Flag.fromValue(record.get(RESERVATIONS.ARRIVED).intValue()),
                VersionKey.of(record.get(RESERVATIONS.VERSION)),
                // 店舗情報
                Store.reconstruct(
                        StoreId.of(record.get(STORES.ID)),
                        CompanyId.of(record.get(STORES.COMPANY_ID)),
                        StoreName.of(record.get(STORES.STORE_NAME)),
                        PostalCode.of(record.get(STORES.POSTAL_CODE)),
                        Address.of(record.get(STORES.ADDRESS)),
                        Latitude.of(record.get(STORES.LATITUDE).doubleValue()),
                        Longitude.of(record.get(STORES.LONGITUDE).doubleValue()),
                        PhoneNumber.of(record.get(STORES.PHONE_NUMBER)),
                        HomePageUrl.of(record.get(STORES.HOME_PAGE_URL)),
                        null
                ),
                reservationMenusMap.isEmpty()
                        ? null
                        : reservationMenusMap.get(record.get(RESERVATIONS.ID))
                        .map(this::recordsToReservationMenu)
        );
    }

    // Result から List<ReservationMenu>を生成
    private ReservationMenu recordsToReservationMenu(Record record) {
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
