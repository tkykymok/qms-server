package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.reservation.FetchReservationsOutput;
import com.qms.mainservice.application.usecase.reservation.FetchReservationsUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.reservation.ReservationPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final FetchReservationsUsecase fetchReservationsUsecase;
    private final ReservationPresenter presenter;

    // 予約一覧を取得する
    @GetMapping("/list")
    public ResponseEntity<?> getReservations() {
        // 店舗ID
        StoreId storeId = StoreId.of(1L);

        // 予約一覧を取得する
        FetchReservationsOutput output = fetchReservationsUsecase.execute(storeId);

        return presenter.present(output);
    }










}