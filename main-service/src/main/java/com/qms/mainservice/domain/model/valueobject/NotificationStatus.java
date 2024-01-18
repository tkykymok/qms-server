package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    UNSENT("未送信", 0),
    SENT("送信済", 1),
    READ("既読", 2),
    FAILED("送信失敗", 9);

    private final String text;
    private final int value;

    NotificationStatus(String text, int value) {
        this.value = value;
        this.text = text;
    }

    public static NotificationStatus fromValue(int value) {
        for (NotificationStatus status : NotificationStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for Status: " + value);
    }
}

