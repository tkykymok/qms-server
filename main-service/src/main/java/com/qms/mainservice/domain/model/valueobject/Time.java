package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record Time(Integer value) implements ValueObject {
    public static Time of(Integer value) {
        return new Time(value);
    }

    public static Time ZERO() {
        return new Time(0);
    }

    public Time add(Time other) {
        return new Time(this.value + other.value);

    }

    public Time subtract(Time other) {
        return new Time(this.value - other.value);
    }

    // 5分単位に丸めて加算する
    public Time addAndRoundUp(Time other) {
        int totalMinutes = this.value + other.value;
        int remainder = totalMinutes % 5;
        if (remainder != 0) {
            // 5分単位に切り上げる
            totalMinutes += 5 - remainder;
        }
        return new Time(totalMinutes);
    }

    public int compareTo(Time other) {
        return this.value.compareTo(other.value);
    }
}