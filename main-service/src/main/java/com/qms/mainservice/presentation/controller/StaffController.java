package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.domain.model.valueobject.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {


    @GetMapping("/store-staff-list")
    public ResponseEntity<?> getStaffList() {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);

        // 店舗IDに紐づくスタッフ一覧&活動スタッフ一覧を取得する



        return null;
    }






}
