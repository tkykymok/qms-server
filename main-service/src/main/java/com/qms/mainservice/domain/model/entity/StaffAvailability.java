package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.Time;
import lombok.Getter;
import lombok.Setter;

// スタッフの次の利用可能時間を保持する優先度キューの要素
@Getter
public class StaffAvailability implements Comparable<StaffAvailability> {
    private ActiveStaff staff; // スタッフ
    private @Setter Time nextAvailableTime; // 次の利用可能時間(何分後か)

    private StaffAvailability() {
    }

    public static StaffAvailability create(ActiveStaff staff, Time nextAvailableTime) {
        StaffAvailability availability = new StaffAvailability();
        availability.staff = staff;
        availability.nextAvailableTime = nextAvailableTime;
        return availability;
    }

    @Override
    public int compareTo(StaffAvailability other) {
        return this.nextAvailableTime.compareTo(other.nextAvailableTime);
    }

    public void addTime(Time time) {
        setNextAvailableTime(nextAvailableTime.addAndRoundUp(time));
    }

    // ループ対象の予約が処理される時間がスタッフの休憩時間内の場合、次の利用可能時間に休憩終了時間を追加する
    public void addBreakTimeIfNeeded() {
        if (staff.isInBreakTime(nextAvailableTime)) {
            setNextAvailableTime(nextAvailableTime.addAndRoundUp(staff.getRemainingTimeToBreakEnd()));
        }
    }

}
