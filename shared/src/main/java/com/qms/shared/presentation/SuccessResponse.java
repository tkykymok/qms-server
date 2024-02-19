package com.qms.shared.presentation;

public record SuccessResponse(
        Message message
) implements BaseResponse {
    @Override
    public String getMessage() {
        return message != null ? message.message() : "";
    }
}
