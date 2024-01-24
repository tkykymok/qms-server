package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActiveStaff extends CompositeKeyBaseEntity<ActiveStaffKey> {
    SortOrder sortOrder; // 並び順
    BreakStartDateTime breakStartDateTime; // 休憩開始時刻
    BreakEndDateTime breakEndDateTime; // 休憩終了時刻
    ReservationId reservationId; // 予約ID

    private ActiveStaff() {
    }

//    public static ActiveStaff create(
//            StoreId storeId,
//            StaffId staffId,
//            SortOrder sortOrder,
//            BreakStartDateTime breakStartDateTime,
//            BreakEndDateTime breakEndDateTime,
//            ReservationId reservationId
//    ) {
//        ActiveStaff activeStaff = new ActiveStaff();
//        activeStaff.key = new ActiveStaffKey(storeId, staffId);
//        activeStaff.sortOrder = sortOrder;
//        activeStaff.breakStartDateTime = breakStartDateTime;
//        activeStaff.breakEndDateTime = breakEndDateTime;
//        activeStaff.reservationId = reservationId;
//        return activeStaff;
//    }

    // 次の利用可能時刻が休憩時間内かどうかを判定する
    public boolean isInBreakTime(Time time) {
        if (breakStartDateTime == null || breakEndDateTime == null) {
            return false;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime nextAvailableDateTime = currentDateTime.plusMinutes(time.value());

        return breakStartDateTime.value().isBefore(nextAvailableDateTime)
                && breakEndDateTime.value().isAfter(nextAvailableDateTime);
    }

    // 休憩終了時刻までの残り時間を取得する
    public Time getRemainingTimeToBreakEnd() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime breakEndDateTime = this.breakEndDateTime.value();
        return Time.of(breakEndDateTime.getMinute() - currentDateTime.getMinute());
    }

    // DBから取得したレコードをActiveStaffに変換する
    public static ActiveStaff reconstruct(
            StoreId storeId,
            StaffId staffId,
            SortOrder sortOrder,
            BreakStartDateTime breakStartDateTime,
            BreakEndDateTime breakEndDateTime,
            ReservationId reservationId
    ) {
        ActiveStaff activeStaff = new ActiveStaff();
        activeStaff.key = new ActiveStaffKey(storeId, staffId);
        activeStaff.sortOrder = sortOrder;
        activeStaff.breakStartDateTime = breakStartDateTime;
        activeStaff.breakEndDateTime = breakEndDateTime;
        activeStaff.reservationId = reservationId;
        return activeStaff;
    }

}
