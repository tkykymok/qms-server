package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.reservation.FetchLastWaitTimeOutput;
import com.qms.mainservice.application.usecase.reservation.FetchReservationsOutput;
import com.qms.mainservice.presentation.web.response.reservation.GetLastWaitTimeResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationsResponse;
import com.qms.mainservice.presentation.web.response.reservation.ReservationResponse;
import com.qms.shared.presentation.Message;
import com.qms.shared.utils.Formatter;
import com.qms.shared.utils.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ReservationPresenter {

    private final MessageHelper messageHelper;

    public ResponseEntity<GetReservationsResponse> present(FetchReservationsOutput output) {
        var response = GetReservationsResponse.builder()
                .reservations(output.reservations().stream()
                        .map(reservation -> ReservationResponse.builder()
                                .reservationId(reservation.reservationId().value())
                                .storeId(reservation.storeId().value())
                                .customerId(reservation.customerId().value())
                                .reservationNumber(reservation.reservationNumber().value())
                                .reservedDate(reservation.reservedDate().value()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .staffId(reservation.staffId().value())
                                .serviceStartDateTime(Formatter.formatDateTime(
                                        reservation.serviceStartDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss"))
                                .serviceEndDateTime(Formatter.formatDateTime(
                                        reservation.serviceEndDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss"))
                                .holdStartDateTime(Formatter.formatDateTime(
                                        reservation.holdStartDateTime().value(),
                                        "yyyy-MM-dd HH:mm:ss"))
                                .status(reservation.status().getValue())
                                .notified(reservation.notified().value())
                                .arrived(reservation.arrived().value())
                                .version(reservation.version().value())
                                .menuName(reservation.menuName().value())
                                .price(reservation.price().value())
                                .time(reservation.time().value())
                                .build())
                        .toList())
                .message(Message.of(messageHelper.getMessage(Locale.JAPAN, "S0001", "予約"))) // TODO メッセージ確認用
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetLastWaitTimeResponse> present(FetchLastWaitTimeOutput output) {
        var response = GetLastWaitTimeResponse.builder()
                .lastWaitTime(output.lastWaitTime().value())
                .reservationNumber(output.reservationNumber().value())
                .build();
        return ResponseEntity.ok(response);
    }
}
