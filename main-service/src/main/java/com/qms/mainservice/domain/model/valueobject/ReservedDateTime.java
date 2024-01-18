package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

import java.time.LocalDateTime;

public record ReservedDateTime(LocalDateTime value) implements ValueObject {
    public static ReservedDateTime of(LocalDateTime value) {
        return new ReservedDateTime(value);
    }

    public static ReservedDateTime now() {
        return new ReservedDateTime(LocalDateTime.now());
    }

    // 今日の日付を取得する
    public static ReservedDateTime today() {
        return new ReservedDateTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
    }
}