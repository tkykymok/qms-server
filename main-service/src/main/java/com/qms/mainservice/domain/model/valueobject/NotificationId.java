package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record NotificationId(Long value) implements BaseId<Long> {
    public static NotificationId of(Long value) {
        return new NotificationId(value);
    }
}