package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public class StoreBusinessHour extends CompositeKeyBaseEntity<StoreBusinessHourKey> {

    private OpenTime openTime;
    private CloseTime closeTime;
    private Flag closed;

    private StoreBusinessHour() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static StoreBusinessHour reconstruct(
            StoreId storeId,
            DayOfWeek dayOfWeek,
            OpenTime openTime,
            CloseTime closeTime,
            Flag closed
    ) {
        StoreBusinessHour storeBusinessHour = new StoreBusinessHour();
        storeBusinessHour.key = StoreBusinessHourKey.of(storeId, dayOfWeek);
        storeBusinessHour.openTime = openTime;
        storeBusinessHour.closeTime = closeTime;
        storeBusinessHour.closed = closed;
        return storeBusinessHour;
    }
}
