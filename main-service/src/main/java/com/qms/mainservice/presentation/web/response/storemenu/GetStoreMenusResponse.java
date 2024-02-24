package com.qms.mainservice.presentation.web.response.storemenu;

import lombok.Builder;

import java.util.List;

@Builder
public record GetStoreMenusResponse(
        List<StoreMenuResponse> storeMenus
) {
}
