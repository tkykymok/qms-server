package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.store.FetchStoresOutput;
import com.qms.mainservice.presentation.web.response.store.SearchStoresResponse;
import com.qms.mainservice.presentation.web.response.store.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class StorePresenter {
    public ResponseEntity<SearchStoresResponse> present(FetchStoresOutput output) {
        var response = SearchStoresResponse.builder()
                .stores(output.stores().stream()
                        .map(store -> StoreResponse.builder()
                                .storeId(store.storeId().value())
                                .companyId(store.companyId().value())
                                .storeName(store.storeName().value())
                                .postalCode(store.postalCode().value())
                                .address(store.address().value())
                                .latitude(store.latitude().value())
                                .longitude(store.longitude().value())
                                .phoneNumber(store.phoneNumber().value())
                                .homePageUrl(store.homePageUrl().value())
                                .isClosed(store.isClosed().value())
                                .weekdayHours(store.weekdayHours())
                                .holidayHours(store.holidayHours())
                                .regularHolidays(store.regularHolidays().stream()
                                        .map((dayOfWeek) -> dayOfWeek.getDisplayName(TextStyle.FULL, Locale.JAPAN))
                                        .toList()
                                )
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(response);
    }
}
