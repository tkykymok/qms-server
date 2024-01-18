package com.qms.mainservice.domain.model.valueobject;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN("管理者", 0),
    CUSTOMER("顧客", 1),
    STAFF("スタッフ", 2);

    private final String text;
    private final int value;

    UserType(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public static UserType fromValue(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value for NotificationType: " + value);
    }
}

