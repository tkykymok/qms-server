package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.reservation.*;
import com.qms.mainservice.presentation.web.response.reservation.*;
import com.qms.shared.presentation.Message;
import com.qms.shared.utils.Formatter;
import com.qms.shared.utils.MessageHelper;
import lombok.RequiredArgsConstructor;
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
                                .serviceStartTime(Formatter.formatTime(
                                        reservation.serviceStartTime().value(), "HH:mm"))
                                .serviceEndTime(Formatter.formatTime(
                                        reservation.serviceEndTime().value(), "HH:mm"))
                                .holdStartTime(Formatter.formatTime(
                                        reservation.holdStartTime().value(), "HH:mm"))
                                .status(reservation.status().getValue())
                                .notified(reservation.notified().value())
                                .arrived(reservation.arrived().value())
                                .version(reservation.version().value())
                                .menuName(reservation.menuName().value())
                                .price(reservation.price().value())
                                .time(reservation.time().value())
                                .customerLastName(reservation.customer().getLastName().value())
                                .customerFirstName(reservation.customer().getFirstName().value())
                                .build())
                        .toList())
                .message(Message.of(messageHelper.getMessage(Locale.JAPAN, "S0001", "予約"))) // TODO メッセージ確認用
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetLastWaitingInfoResponse> present(FetchLastWaitTimeOutput output) {
        var response = GetLastWaitingInfoResponse.builder()
                .waitingInfo(WaitingInfoResponse.builder()
                        .lastWaitTime(output.lastWaitTime().value())
                        .reservationNumber(output.reservationNumber().value())
                        .activeStaffCount(output.activeStaffCount().value())
                        .waitingCount(output.waitingCount().value())
                        .build())
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetReservationDetailResponse> present(FetchReservationDetailOutput output) {
        var response = GetReservationDetailResponse.builder()
                .reservation(ReservationResponse.builder()
                        .reservationId(output.reservation().reservationId().value())
                        .storeId(output.reservation().storeId().value())
                        .customerId(output.reservation().customerId().value())
                        .reservationNumber(output.reservation().reservationNumber().value())
                        .reservedDate(output.reservation().reservedDate().value()
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E)", Locale.JAPAN)))
                        .status(output.reservation().status().getValue())
                        .arrived(output.reservation().arrived().value())
                        .version(output.reservation().version().value())
                        .storeName(output.reservation().store().getStoreName().value())
                        .homePageUrl(output.reservation().store().getHomePageUrl().value())
                        .menuName(output.reservation().menuName().value())
                        .price(output.reservation().price().value())
                        .customerFirstName(output.reservation().customer().getFirstName().value())
                        .customerLastName(output.reservation().customer().getLastName().value())
                        .build())
                .waitingInfo(
                        WaitingInfoResponse.builder()
                                .waitingCount(output.waitingCount().value())
                                .position(output.position().value())
                                .reservationNumber(output.reservationNumber().value())
                                .estimatedServiceStartTime(Formatter.formatTime(
                                        output.estimatedServiceStartTime().value(), "HH:mm"))
                                .build())
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UpdateReservationStatusResponse> present(UpdateReservationStatusOutput output) {
        var response = UpdateReservationStatusResponse.builder()
                .reservation(ReservationResponse.builder()
                        .reservationId(output.reservation().reservationId().value())
                        .storeId(output.reservation().storeId().value())
                        .customerId(output.reservation().customerId().value())
                        .reservationNumber(output.reservation().reservationNumber().value())
                        .reservedDate(output.reservation().reservedDate().value()
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E)", Locale.JAPAN)))
                        .status(output.reservation().status().getValue())
                        .arrived(output.reservation().arrived().value())
                        .version(output.reservation().version().value())
                        .storeName(output.reservation().store().getStoreName().value())
                        .homePageUrl(output.reservation().store().getHomePageUrl().value())
                        .menuName(output.reservation().menuName().value())
                        .price(output.reservation().price().value())
                        .customerFirstName(output.reservation().customer().getFirstName().value())
                        .customerLastName(output.reservation().customer().getLastName().value())
                        .build());
        return ResponseEntity.ok(response.build());
    }

}
