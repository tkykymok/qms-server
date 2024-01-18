package com.qms.mainservice.domain.model.valueobject;

import com.qms.mainservice.domain.repository.reservation.ReservationRepository;
import com.qms.shared.domain.model.ValueObject;

public record ReservationNumber(Integer value) implements ValueObject {
    public static ReservationNumber of(Integer value) {
        return new ReservationNumber(value);
    }

    // 予約番号を採番する
    public static ReservationNumber newReservationNumber(ReservationRepository repository) {
        return  repository.newReservationNumber();
    }
}