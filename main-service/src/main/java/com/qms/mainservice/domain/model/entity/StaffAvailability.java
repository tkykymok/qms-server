package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.Time;
import lombok.Getter;
import lombok.Setter;

// スタッフの次の利用可能時刻を保持する優先度キューの要素
@Getter
public class StaffAvailability implements Comparable<StaffAvailability> {
    private ActiveStaff staff;
    private @Setter Time nextAvailableTime;

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
}
