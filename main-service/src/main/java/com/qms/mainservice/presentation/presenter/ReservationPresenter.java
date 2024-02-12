package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.reservation.*;
import com.qms.mainservice.domain.model.valueobject.Position;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.presentation.web.response.reservation.*;
import com.qms.shared.presentation.Message;
import com.qms.shared.utils.Formatter;
import com.qms.shared.utils.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationPresenter {

    private final MessageHelper messageHelper;

    public ResponseEntity<GetReservationsResponse> present(FetchReservationsOutput output) {
        var reservations = output.reservations().stream()
                .map(this::createReservationResponse)
                .toList();

        var response = GetReservationsResponse.builder()
                .reservations(reservations)
                .message(Message.of(messageHelper.getMessage(Locale.JAPAN, "S0001", "予約"))) // TODO メッセージ確認用
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetLastWaitingInfoResponse> present(FetchLastWaitTimeOutput output) {
        var response = GetLastWaitingInfoResponse.builder()
                .waitingInfo(createWaitingInfoResponse(output.waitingInfo()))
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetReservationDetailResponse> present(FetchReservationDetailOutput output) {
        var response = GetReservationDetailResponse.builder()
                .reservation(createReservationResponse(output.reservation()))
                .waitingInfo(createWaitingInfoResponse(output.waitingInfo()))
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UpdateReservationStatusResponse> present(UpdateReservationStatusOutput output) {
        var response = UpdateReservationStatusResponse.builder()
                .reservation(createReservationResponse(output.reservation()));
        return ResponseEntity.ok(response.build());
    }

    private ReservationResponse createReservationResponse(ReservationOutput reservationOutput) {
        return ReservationResponse.builder()
                .reservationId(reservationOutput.reservationId().value())
                .storeId(reservationOutput.storeId().value())
                .customerId(reservationOutput.customerId().value())
                .reservationNumber(reservationOutput.reservationNumber().value())
                .reservedDate(reservationOutput.reservedDate().value().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .staffId(Optional.ofNullable(reservationOutput.staffId())
                        .map(StaffId::value)
                        .orElse(null))
                .serviceStartTime(Optional.ofNullable(reservationOutput.serviceStartTime())
                        .map(time -> Formatter.formatTime(time.value(), "HH:mm"))
                        .orElse(null))
                .serviceEndTime(Optional.ofNullable(reservationOutput.serviceEndTime())
                        .map(time -> Formatter.formatTime(time.value(), "HH:mm"))
                        .orElse(null))
                .holdStartTime(Optional.ofNullable(reservationOutput.holdStartTime())
                        .map(time -> Formatter.formatTime(time.value(), "HH:mm"))
                        .orElse(null))
                .status(reservationOutput.status().getValue())
                .notified(reservationOutput.notified().value())
                .arrived(reservationOutput.arrived().value())
                .version(reservationOutput.version().value())
                .menuName(reservationOutput.menuName().value())
                .price(reservationOutput.price().value())
                .time(reservationOutput.time().value())
                .tagColor(reservationOutput.tagColor().getHexCode())
                .customerLastName(reservationOutput.customer().getLastName().value())
                .customerFirstName(reservationOutput.customer().getFirstName().value())
                .build();
    }

    private WaitingInfoResponse createWaitingInfoResponse(WaitingInfoOutput waitingInfoOutput) {
        return WaitingInfoResponse.builder()
                .waitTime(waitingInfoOutput.waitTime().value())
                .reservationNumber(waitingInfoOutput.reservationNumber().value())
                .activeStaffCount(waitingInfoOutput.activeStaffCount().value())
                .waitingCount(waitingInfoOutput.waitingCount().value())
                .estimatedServiceStartTime(Optional.ofNullable(waitingInfoOutput.estimatedServiceStartTime())
                        .map(time -> Formatter.formatTime(time.value(), "HH:mm"))
                        .orElse(null))
                .position(Optional.ofNullable(waitingInfoOutput.position())
                        .map(Position::value)
                        .orElse(null))
                .build();
    }


}
