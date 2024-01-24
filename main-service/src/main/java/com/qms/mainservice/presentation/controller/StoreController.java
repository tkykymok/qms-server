package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.store.FetchStoresInput;
import com.qms.mainservice.application.usecase.store.FetchStoresOutput;
import com.qms.mainservice.application.usecase.store.FetchStoresUsecase;
import com.qms.mainservice.presentation.presenter.StorePresenter;
import com.qms.mainservice.presentation.web.request.SearchStoresRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final FetchStoresUsecase fetchStoresUsecase;
    private final StorePresenter presenter;

    @GetMapping("/search")
    public ResponseEntity<?> searchStores(SearchStoresRequest request) {
        // リクエストを入力値に変換する
        var input = FetchStoresInput.builder()
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();

        // 店舗一覧を取得する
        FetchStoresOutput output = fetchStoresUsecase.execute(input);

        return presenter.present(output);
    }


}
