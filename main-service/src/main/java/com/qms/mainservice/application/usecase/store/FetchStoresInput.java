package com.qms.mainservice.application.usecase.store;

import lombok.Builder;

@Builder
public record FetchStoresInput(
        double latitude,
        double longitude
) {
}
