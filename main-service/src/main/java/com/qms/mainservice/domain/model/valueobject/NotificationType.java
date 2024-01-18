package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum NotificationType {
    RESERVATION("予約", 1),
    PROMOTION("販促", 2);

    private final String text;
    private final int value;

    NotificationType(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public static NotificationType fromValue(int value) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value for NotificationType: " + value);
    }
}

