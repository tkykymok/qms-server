package com.qms.mainservice.presentation.web.request.auth;

import com.qms.mainservice.application.usecase.auth.AuthenticateInput;
import com.qms.mainservice.domain.model.valueobject.Password;
import com.qms.mainservice.domain.model.valueobject.Username;
import com.qms.shared.domain.model.valueobject.UserType;

public record SignInRequest(
        String username,
        String password,
        Integer userType
) {
    // inputクラスに変換
    public AuthenticateInput toInput() {
        return AuthenticateInput.builder()
                .username(Username.of(username))
                .password(Password.of(password))
                .userType(UserType.fromValue(userType))
                .build();
    }

}
