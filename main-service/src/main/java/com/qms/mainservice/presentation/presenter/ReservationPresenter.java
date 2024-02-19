package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.reservation.*;
import com.qms.mainservice.domain.model.valueobject.Position;
import com.qms.mainservice.domain.model.valueobject.StaffId;
import com.qms.mainservice.presentation.web.response.reservation.*;
import com.qms.shared.presentation.BasePresenter;
import com.qms.shared.utils.Formatter;
import com.qms.shared.utils.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationPresenter extends BasePresenter {

    private final MessageHelper messageHelper;

    public ResponseEntity<GetReservationsResponse> present(FetchReservationsOutput output) {
        return ResponseEntity.ok(getReservationsResponse(output));
    }
    public ResponseEntity<GetLastWaitingInfoResponse> present(FetchLastWaitTimeOutput output) {
        return ResponseEntity.ok(getLastWaitingInfoResponse(output));
    }
    public ResponseEntity<GetReservationDetailResponse> present(FetchReservationDetailOutput output) {
        return ResponseEntity.ok(getReservationDetailResponse(output));
    }
    public ResponseEntity<UpdateReservationStatusResponse> present(UpdateReservationStatusOutput output) {
        return ResponseEntity.ok(updateReservationStatusResponse(output));
    }

    private GetReservationsResponse getReservationsResponse(FetchReservationsOutput output) {
        var reservations = output.reservations().stream()
                .map(this::createReservationResponse)
                .toList();

        return GetReservationsResponse.builder()
                .reservations(reservations)
                .build();
    }

    private GetLastWaitingInfoResponse getLastWaitingInfoResponse(FetchLastWaitTimeOutput output) {
        return GetLastWaitingInfoResponse.builder()
                .waitingInfo(createWaitingInfoResponse(output.waitingInfo()))
                .build();
    }

    private GetReservationDetailResponse getReservationDetailResponse(FetchReservationDetailOutput output) {
        return GetReservationDetailResponse.builder()
                .reservation(createReservationResponse(output.reservation()))
                .waitingInfo(createWaitingInfoResponse(output.waitingInfo()))
                .build();
    }

    private UpdateReservationStatusResponse updateReservationStatusResponse(UpdateReservationStatusOutput output) {
        return UpdateReservationStatusResponse.builder()
                .reservation(createReservationResponse(output.reservation()))
                .build();
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
                .reservationMenus(reservationOutput.reservationMenus().stream()
                        .map(this::createReservationMenuResponse)
                        .toList())
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

    private ReservationMenuResponse createReservationMenuResponse(ReservationMenuOutput reservationMenuOutput) {
        return ReservationMenuResponse.builder()
                .storeMenuId(reservationMenuOutput.storeMenuId().value())
                .menuName(reservationMenuOutput.menuName().value())
                .price(reservationMenuOutput.price().value())
                .time(reservationMenuOutput.time().value())
                .tagColor(reservationMenuOutput.tagColor().getHexCode())
                .build();
    }

}
