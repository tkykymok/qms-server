package com.qms.mainservice.application.usecase.auth;

import com.qms.mainservice.domain.model.valueobject.Password;
import com.qms.mainservice.domain.model.valueobject.Username;
import com.qms.shared.domain.model.valueobject.UserType;
import lombok.Builder;

@Builder
public record AuthenticateInput(
        Username username,
        Password password,
        UserType userType
) {
}
