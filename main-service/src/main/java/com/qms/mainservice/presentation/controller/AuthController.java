package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.auth.AuthenticateUsecase;
import com.qms.mainservice.presentation.presenter.AuthPresenter;
import com.qms.mainservice.presentation.web.request.auth.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUsecase authenticateUsecase;

    private final AuthPresenter presenter;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        var input = request.toInput();
        var output = authenticateUsecase.execute(input);

        return presenter.present(output);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp() {
        return null;
    }


}
