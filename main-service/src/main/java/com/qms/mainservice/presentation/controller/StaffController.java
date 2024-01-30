package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.staff.FetchStoreStaffsOutput;
import com.qms.mainservice.application.usecase.staff.FetchStoreStaffsUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.StaffPresenter;
import com.qms.mainservice.presentation.web.response.staff.GetStoreStaffs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final FetchStoreStaffsUsecase fetchStoreStaffsUsecase;
    private final StaffPresenter presenter;


    @GetMapping("/store-staffs")
    public ResponseEntity<GetStoreStaffs> getStoreStaffs() {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // 店舗IDに紐づくスタッフ一覧&活動スタッフ一覧を取得する
        FetchStoreStaffsOutput output = fetchStoreStaffsUsecase.execute(storeId);

        return presenter.present(output);
    }


}
