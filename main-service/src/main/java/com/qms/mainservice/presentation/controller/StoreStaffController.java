package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.storestaff.*;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.StoreStaffPresenter;
import com.qms.mainservice.presentation.web.request.storestaff.SortActiveStaffsRequest;
import com.qms.mainservice.presentation.web.request.storestaff.ToggleActiveStaffRequest;
import com.qms.mainservice.presentation.web.request.storestaff.UpdateBreakTimeRequest;
import com.qms.mainservice.presentation.web.response.storestaff.GetStoreStaffs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-staff")
public class StoreStaffController {

    private final FetchStoreStaffsUsecase fetchStoreStaffsUsecase;
    private final SortActiveStaffsUsecase sortActiveStaffsUsecase;
    private final CreateOrRemoveActiveStaffUsecase createOrRemoveActiveStaffUsecase;
    private final UpdateBreakTimeUsecase updateBreakTimeUsecase;
    private final StoreStaffPresenter presenter;


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

    // スタッフの活動状態を更新する
    @PutMapping("/toggle-active-staff")
    public ResponseEntity<Void> toggleActiveStaff(@RequestBody ToggleActiveStaffRequest request) {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // リクエストをInputに変換する
        var input = request.toInput(storeId);
        // スタッフの活動状態を更新する
        createOrRemoveActiveStaffUsecase.execute(input);
        return ResponseEntity.ok().build();
    }

    // 活動スタッフの休憩時間を登録する
    @PutMapping("/update-break-time")
    public ResponseEntity<Void> updateBreakTime(@RequestBody UpdateBreakTimeRequest request) {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // リクエストをInputに変換する
        var input = request.toInput(storeId);
        // 活動スタッフの休憩時間を更新する
        updateBreakTimeUsecase.execute(input);
        // TODO
        return ResponseEntity.ok().build();
    }

}
