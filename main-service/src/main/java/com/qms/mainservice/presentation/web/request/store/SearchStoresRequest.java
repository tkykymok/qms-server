package com.qms.mainservice.presentation.web.request.store;

public record SearchStoresRequest(
        double latitude,
        double longitude
) {
}
