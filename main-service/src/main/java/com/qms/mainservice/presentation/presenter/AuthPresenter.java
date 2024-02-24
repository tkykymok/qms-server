package com.qms.mainservice.presentation.presenter;

import com.qms.mainservice.application.usecase.auth.AuthenticateOutput;
import com.qms.mainservice.presentation.web.response.auth.SignInResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthPresenter {
    public ResponseEntity<SignInResponse> present(AuthenticateOutput output) {
        var response = SignInResponse.builder()
                .accessToken(output.accessToken().value())
                .idToken(output.idToken().value())
                .refreshToken(output.refreshToken().value())
                .expiresIn(output.expiresIn())
                .build();

        return ResponseEntity.ok(response);

    }
}
