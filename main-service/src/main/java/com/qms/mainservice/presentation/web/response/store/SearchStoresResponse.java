package com.qms.mainservice.presentation.web.response.store;

import com.qms.shared.presentation.BaseResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchStoresResponse(
        List<StoreResponse> stores
) implements BaseResponse {
    @Override
    public String getMessage() {
        return "店舗一覧を取得しました。";
    }
}
