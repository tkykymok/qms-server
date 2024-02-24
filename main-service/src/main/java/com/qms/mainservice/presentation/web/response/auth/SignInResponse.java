package com.qms.mainservice.presentation.web.response.auth;

import lombok.Builder;

@Builder
public record SignInResponse(
        String idToken,
        String refreshToken,
        Integer expiresIn
) {
}
