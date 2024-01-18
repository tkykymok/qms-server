package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record ReservationNumber(Integer value) implements ValueObject {
    public static ReservationNumber of(Integer value) {
        return new ReservationNumber(value);
    }
}