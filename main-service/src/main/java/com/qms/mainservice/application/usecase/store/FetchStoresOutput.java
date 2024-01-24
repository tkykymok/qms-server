package com.qms.mainservice.application.usecase.store;

import lombok.Builder;

import java.util.List;

@Builder
public record FetchStoresOutput(
        List<StoreOutput> stores
) {
}
