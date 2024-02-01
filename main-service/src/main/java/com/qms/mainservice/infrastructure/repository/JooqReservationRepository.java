package com.qms.mainservice.infrastructure.repository;

import com.qms.mainservice.domain.model.aggregate.Reservation;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.mainservice.domain.repository.ReservationRepository;
import com.qms.mainservice.infrastructure.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

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
                .innerJoin(CUSTOMERS).on(RESERVATIONS.CUSTOMER_ID.eq(CUSTOMERS.ID))
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

        return ReservationMapper.recordToReservation(reservationRecord, reservationMenuMap);
    }

    @Override
    public Reservation findByCustomerId(CustomerId customerId) {
        // 顧客IDに紐づく予約を取得する
        Record reservationRecord = dsl.select()
                .from(RESERVATIONS)
                .innerJoin(STORES).on(RESERVATIONS.STORE_ID.eq(STORES.ID))
                .innerJoin(CUSTOMERS).on(RESERVATIONS.CUSTOMER_ID.eq(CUSTOMERS.ID))
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

        return ReservationMapper.recordToReservation(reservationRecord, reservationMenuMap);
    }

    @Override
    public List<Reservation> findAllByStoreIdAndReservedDate(StoreId storeId, ReservedDate reservedDate) {
        // 予約一覧を取得する
        Result<Record> reservationRecords = dsl.select()
                .from(RESERVATIONS)
                .innerJoin(STORES).on(RESERVATIONS.STORE_ID.eq(STORES.ID))
                .innerJoin(CUSTOMERS).on(RESERVATIONS.CUSTOMER_ID.eq(CUSTOMERS.ID))
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
                .map(record -> ReservationMapper.recordToReservation(record, reservationMenuMap));
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

    @Override
    public void update(Reservation reservation) {
        // 予約を更新する
        dsl.update(RESERVATIONS)
                .set(RESERVATIONS.STAFF_ID, reservation.getStaffId().value()) // 対応スタッフID
                .set(RESERVATIONS.SERVICE_START_TIME, reservation.getServiceStartTime().value()) // 対応開始時刻
                .set(RESERVATIONS.SERVICE_END_TIME, reservation.getServiceEndTime().value()) // 対応終了時刻
                .set(RESERVATIONS.HOLD_START_TIME, reservation.getHoldStartTime().value()) // 保留開始時刻
                .set(RESERVATIONS.STATUS, reservation.getStatus().getValue()) // 予約ステータス
                .set(RESERVATIONS.NOTIFIED, reservation.getNotified().toByteValue()) // 通知フラグ
                .set(RESERVATIONS.ARRIVED, reservation.getArrived().toByteValue()) // 到着フラグ
                .set(RESERVATIONS.VERSION, reservation.getVersion().value()) // バージョン
                .where(RESERVATIONS.ID.eq(reservation.getId().value())) //
                .execute();
    }


}
