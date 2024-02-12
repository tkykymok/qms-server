package com.qms.mainservice.presentation.web.response.storemenu;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StoreMenuResponse(
        Long storeId,
        Integer storeMenuId,
        String menuName,
        BigDecimal price,
        Integer time,
        Boolean disabled
) {
}
