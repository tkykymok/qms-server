package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public record ServiceStartTime(LocalTime value) implements ValueObject {
    public static ServiceStartTime of(LocalTime value) {
        return new ServiceStartTime(value);
    }

    public static ServiceStartTime now() {
        return new ServiceStartTime(LocalTime.now());
    }

    // 現在時間にTimeオブジェクトを加算し、5分刻みで丸めるメソッド
    public static ServiceStartTime nowPlusTime(Time time) {
        // 現在の時間にminutesToAddを加算
        LocalTime newTime = LocalTime.now().plusMinutes(time.value());

        // 5分単位で切り上げる
        int remainder = newTime.getMinute() % 5;
        if (remainder != 0) {
            newTime = newTime.plusMinutes(5 - remainder)
                    .truncatedTo(ChronoUnit.MINUTES);
        }

        return new ServiceStartTime(newTime);
    }
}