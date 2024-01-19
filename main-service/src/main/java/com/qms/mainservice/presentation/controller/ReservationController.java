package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.domain.model.valueobject.StoreId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    // 予約一覧を取得する
    @GetMapping("/list")
    public String getReservationList() {
        // 店舗ID
        StoreId storeId = StoreId.of(1L);

        // 予約一覧を取得する

        return "予約一覧を取得する";
    }










}
