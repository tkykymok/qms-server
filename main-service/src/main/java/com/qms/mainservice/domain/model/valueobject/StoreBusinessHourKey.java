package com.qms.mainservice.domain.model.valueobject;

import java.io.Serializable;
import java.time.DayOfWeek;

public record StoreBusinessHourKey(
        StoreId storeId,
        DayOfWeek dayOfWeek
) implements Serializable {
    public static StoreBusinessHourKey of(StoreId storeId, DayOfWeek dayOfWeek) {
        return new StoreBusinessHourKey(storeId, dayOfWeek);
    }
}
