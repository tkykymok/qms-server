package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    SUNDAY("日曜日", 1),
    MONDAY("月曜日", 2),
    TUESDAY("火曜日", 3),
    WEDNESDAY("水曜日", 4),
    THURSDAY("木曜日", 5),
    FRIDAY("金曜日", 6),
    SATURDAY("土曜日", 7);

    private final String text;
    private final int value;

    DayOfWeek(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getShortText() {
        return text.substring(0, 1);
    }

    public static DayOfWeek fromValue(int value) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid value for DayOfWeek: " + value);
    }
}
