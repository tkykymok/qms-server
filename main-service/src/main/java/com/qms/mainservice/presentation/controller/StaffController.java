package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.store.FetchStoresInput;
import com.qms.mainservice.application.usecase.store.FetchStoresOutput;
import com.qms.mainservice.application.usecase.store.FetchStoresUsecase;
import com.qms.mainservice.domain.model.valueobject.StoreId;
import com.qms.mainservice.presentation.presenter.StorePresenter;
import com.qms.mainservice.presentation.web.request.SearchStoresRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {






}
