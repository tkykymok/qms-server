package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ActiveStaff extends CompositeKeyBaseEntity<ActiveStaffKey> {
    SortOrder sortOrder; // 並び順
    BreakStartTime breakStartTime; // 休憩開始時間
    BreakEndTime breakEndTime; // 休憩終了時間
    ReservationId reservationId; // 予約ID

    private ActiveStaff() {
    }

//    public static ActiveStaff create(
//
//    ) {
//    }

    // 次の利用可能時間が休憩時間内かどうかを判定する
    public boolean isInBreakTime(Time time) {
        if (breakStartTime.value() == null || breakEndTime.value() == null) {
            return false;
        }

        LocalTime currentTime = LocalTime.now();
        LocalTime nextAvailableTime = currentTime.plusMinutes(time.value());

        return breakStartTime.value().isBefore(nextAvailableTime)
                && breakEndTime.value().isAfter(nextAvailableTime);
    }

    // 休憩終了時間までの残り時間を取得する
    public Time getRemainingTimeToBreakEnd() {
        LocalTime currentTime = LocalTime.now();
        LocalTime breakEndTime = this.breakEndTime.value();
        return Time.of(breakEndTime.getMinute() - currentTime.getMinute());
    }

    // DBから取得したレコードをActiveStaffに変換する
    public static ActiveStaff reconstruct(
            StoreId storeId,
            StaffId staffId,
            SortOrder sortOrder,
            BreakStartTime breakStartTime,
            BreakEndTime breakEndTime,
            ReservationId reservationId
    ) {
        ActiveStaff activeStaff = new ActiveStaff();
        activeStaff.key = new ActiveStaffKey(storeId, staffId);
        activeStaff.sortOrder = sortOrder;
        activeStaff.breakStartTime = breakStartTime;
        activeStaff.breakEndTime = breakEndTime;
        activeStaff.reservationId = reservationId;
        return activeStaff;
    }

}
