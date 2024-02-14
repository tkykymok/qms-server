package com.qms.mainservice.presentation.web.response.storemenu;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StoreMenuResponse(
        Integer storeMenuId,
        String menuName,
        BigDecimal price,
        Integer time,
        String tagColor,
        Boolean disabled
) {
}
