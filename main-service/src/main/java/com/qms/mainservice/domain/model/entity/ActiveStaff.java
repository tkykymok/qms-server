package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Getter
public class ActiveStaff extends CompositeKeyBaseEntity<ActiveStaffKey> {
    private @Setter SortOrder sortOrder; // 並び順
    private BreakStartTime breakStartTime; // 休憩開始時間
    private BreakEndTime breakEndTime; // 休憩終了時間
    // スタッフ
    private Staff staff;

    private ActiveStaff() {
    }

    public static ActiveStaff create(
            StoreId storeId,
            StaffId staffId,
            SortOrder sortOrder
    ) {
        ActiveStaff activeStaff = new ActiveStaff();
        activeStaff.key = new ActiveStaffKey(storeId, staffId);
        activeStaff.sortOrder = sortOrder;
        activeStaff.trackingInfo = TrackingInfo.create();
        return activeStaff;
    }

    // 休憩時間を設定する
    public void setBreakTime(
            BreakStartTime breakStartTime,
            BreakEndTime breakEndTime
    ) {
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    // 次の利用可能時間が休憩時間内かどうかを判定する
    public boolean isInBreakTime(Time time) {
        if (breakStartTime == null || breakEndTime == null) {
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
            TrackingInfo trackingInfo,
            Staff staff
    ) {
        ActiveStaff activeStaff = new ActiveStaff();
        activeStaff.key = new ActiveStaffKey(storeId, staffId);
        activeStaff.sortOrder = sortOrder;
        activeStaff.breakStartTime = breakStartTime;
        activeStaff.breakEndTime = breakEndTime;
        activeStaff.trackingInfo = trackingInfo;
        activeStaff.staff = staff;
        return activeStaff;
    }

}
