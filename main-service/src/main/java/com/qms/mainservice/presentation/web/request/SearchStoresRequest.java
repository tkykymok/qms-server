package com.qms.mainservice.presentation.web.request;

public record SearchStoresRequest(
        double latitude,
        double longitude
) {
}
