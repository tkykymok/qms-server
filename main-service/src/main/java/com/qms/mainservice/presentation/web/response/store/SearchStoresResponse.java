package com.qms.mainservice.presentation.web.response.store;

import com.qms.shared.presentation.BaseResponse;
import com.qms.shared.presentation.Message;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchStoresResponse(
        List<StoreResponse> stores,
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null
                ? message.message()
                : "";
    }
}
