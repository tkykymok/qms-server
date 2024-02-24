package com.qms.mainservice.application.usecase.auth;

import com.qms.mainservice.domain.model.valueobject.AccessToken;
import com.qms.mainservice.domain.model.valueobject.IdToken;
import com.qms.mainservice.domain.model.valueobject.RefreshToken;
import lombok.Builder;

@Builder
public record AuthenticateOutput(
        AccessToken accessToken,
        RefreshToken refreshToken,
        IdToken idToken,
        Integer expiresIn
) {
}
