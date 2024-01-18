package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    WAITING("未案内", 0),
    IN_PROGRESS("対応中", 1),
    DONE("案内済", 2),
    PENDING("保留", 5),
    CANCELLED("キャンセル", 9);

    private final String text;
    private final int value;

    ReservationStatus(String text, int value) {
        this.value = value;
        this.text = text;
    }

    public static ReservationStatus fromValue(int value) {
        for (ReservationStatus status : ReservationStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for Status: " + value);
    }
}

