package com.qms.mainservice.domain.model.entity;

import com.qms.mainservice.domain.model.valueobject.Flag;
import com.qms.mainservice.domain.model.valueobject.StoreBusinessHourKey;
import com.qms.mainservice.domain.model.valueobject.Time;
import com.qms.shared.domain.model.CompositeKeyBaseEntity;

public class StoreBusinessHour extends CompositeKeyBaseEntity<StoreBusinessHourKey> {

    private Time openTime;
    private Time closeTime;
    private Flag closed;

    private StoreBusinessHour() {
    }


    // DBから取得したデータをドメインオブジェクトに変換する
    public static StoreBusinessHour reconstruct(
            StoreBusinessHourKey storeBusinessHourKey,
            Time openTime,
            Time closeTime,
            Flag closed
    ) {
        StoreBusinessHour storeBusinessHour = new StoreBusinessHour();
        storeBusinessHour.key = storeBusinessHourKey;
        storeBusinessHour.openTime = openTime;
        storeBusinessHour.closeTime = closeTime;
        storeBusinessHour.closed = closed;
        return storeBusinessHour;
    }
}
