package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record NotificationContent(String value) implements ValueObject {
    public static NotificationContent of(String value) {
        return new NotificationContent(value);
    }
}