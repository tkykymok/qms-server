package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.storemenu.FetchStoreMenusOutput;
import com.qms.mainservice.application.usecase.storemenu.FetchStoreMenusUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.StoreMenuPresenter;
import com.qms.mainservice.presentation.web.response.storemenu.GetStoreMenusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-menu")
public class StoreMenuController {

    private final FetchStoreMenusUsecase fetchStoreMenusUsecase;
    private final StoreMenuPresenter presenter;


    @GetMapping("/list")
    public ResponseEntity<GetStoreMenusResponse> getStoreMenus() {
        // 店舗ID TODO tokenから取得する想定
        StoreId storeId = StoreId.of(1L);
        // 店舗IDに紐づく店舗メニュー一覧を取得する
        FetchStoreMenusOutput output = fetchStoreMenusUsecase.execute(storeId);

        return presenter.present(output);
    }


}
