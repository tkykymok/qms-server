package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.reservation.FetchLastWaitTimeOutput;
import com.qms.mainservice.application.usecase.reservation.FetchLastWaitTimeUsecase;
import com.qms.mainservice.application.usecase.reservation.FetchReservationsOutput;
import com.qms.mainservice.application.usecase.reservation.FetchReservationsUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.ReservationPresenter;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationDetailResponse;
import com.qms.mainservice.presentation.web.response.reservation.GetReservationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final FetchReservationsUsecase fetchReservationsUsecase;
    private final FetchLastWaitTimeUsecase fetchLastWaitTimeUsecase;
    private final ReservationPresenter presenter;

    // 予約一覧を取得する
    @GetMapping("/list")
    public ResponseEntity<GetReservationsResponse> getReservations() {
        // 店舗ID
        StoreId storeId = StoreId.of(1L);

        // 予約一覧を取得する
        FetchReservationsOutput output = fetchReservationsUsecase.execute(storeId);

        return presenter.present(output);
    }

    // 予約の最後尾の待ち時間を取得する
    @GetMapping("/last-wait-time/{storeId}")
    public ResponseEntity<?> getLastWaitTime(@PathVariable("storeId") Long storeId) {
        FetchLastWaitTimeOutput output = fetchLastWaitTimeUsecase.execute(StoreId.of(storeId));
        return presenter.present(output);
    }

    // 予約の待ち時間を取得する
    @GetMapping("/detail/{reservationId}")
    public ResponseEntity<GetReservationDetailResponse> getReservationDetail(
            @PathVariable("reservationId") Long reservationId) {
        System.out.println(reservationId);

        return null;
    }


}
