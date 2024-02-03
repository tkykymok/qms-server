package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.storestaff.FetchStoreStaffsOutput;
import com.qms.mainservice.application.usecase.storestaff.FetchStoreStaffsUsecase;
import com.qms.mainservice.application.usecase.storestaff.SortActiveStaffsUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.StaffPresenter;
import com.qms.mainservice.presentation.web.request.storestaff.SortActiveStaffsRequest;
import com.qms.mainservice.presentation.web.response.staff.GetStoreStaffs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-staff")
public class StoreStaffController {

    private final FetchStoreStaffsUsecase fetchStoreStaffsUsecase;
    private final SortActiveStaffsUsecase sortActiveStaffsUsecase;
    private final StaffPresenter presenter;


    @GetMapping("/list")
    public ResponseEntity<GetStoreStaffs> getStoreStaffs() {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // 店舗IDに紐づくスタッフ一覧&活動スタッフ一覧を取得する
        FetchStoreStaffsOutput output = fetchStoreStaffsUsecase.execute(storeId);

        return presenter.present(output);
    }

    // 活動中スタッフの並び順を更新する
    @PutMapping("/sort-active-staffs")
    public ResponseEntity<Void> sortActiveStaffs(@RequestBody SortActiveStaffsRequest request) {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // リクエストをInputに変換する
        var input = request.toInput(storeId);
        // 活動中スタッフの並び順を更新する
        sortActiveStaffsUsecase.execute(input);
        return ResponseEntity.ok().build();
    }

}
