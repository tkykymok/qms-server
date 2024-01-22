package com.qms.mainservice.presentation.presenter.reservation;

import com.qms.mainservice.application.usecase.reservation.FetchReservationsOutput;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationsResponse;
import com.qms.mainservice.presentation.web.response.reservation.ReservationResponse;
import com.qms.shared.utils.Formatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationPresenter {
    public ResponseEntity<GetReservationsResponse> present(FetchReservationsOutput output) {
        var response = GetReservationsResponse.builder()
                .reservations(output.reservations().stream()
                        .map(reservation -> ReservationResponse.builder()
                                .reservationId(reservation.reservationId().value())
                                .storeId(reservation.storeId().value())
                                .customerId(reservation.customerId().value())
                                .reservationNumber(reservation.reservationNumber().value())
                                .reservedDate(reservation.reservedDate().value()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                )
                                .staffId(reservation.staffId().value())
                                .serviceStartDateTime(Formatter.formatDateTime(
                                        reservation.serviceStartDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss")
                                )
                                .serviceEndDateTime(Formatter.formatDateTime(
                                        reservation.serviceEndDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss")
                                )
                                .holdStartDateTime(Formatter.formatDateTime(
                                        reservation.holdStartDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss")
                                )
                                .status(reservation.status().getValue())
                                .notified(reservation.notified().value())
                                .arrived(reservation.arrived().value())
                                .version(reservation.version().value())
                                .build())
                        .toList())
                .build();
        return ResponseEntity.ok(response);
    }
}
