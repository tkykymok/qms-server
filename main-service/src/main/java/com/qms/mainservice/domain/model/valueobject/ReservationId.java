package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record ReservationId(Long value) implements BaseId<Long> {
    public static ReservationId of(Long value) {
        if(value == null) return null;
        return new ReservationId(value);
    }
}