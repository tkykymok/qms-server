package com.qms.mainservice.presentation.controller;

import com.qms.mainservice.application.usecase.auth.AuthenticateUsecase;
import com.qms.mainservice.presentation.presenter.AuthPresenter;
import com.qms.mainservice.presentation.web.request.auth.SignInRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUsecase authenticateUsecase;

    private final AuthPresenter presenter;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request, HttpServletResponse response) {
        var input = request.toInput();
        var output = authenticateUsecase.execute(input);

        if (output != null) {
            saveTokenInCookie("accessToken", output.accessToken().value(), response);
            saveTokenInCookie("refreshToken", output.refreshToken().value(), response);
            saveTokenInCookie("idToken", output.idToken().value(), response);
        }

        return presenter.present(Objects.requireNonNull(output));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp() {
        return null;
    }


    /**
     * Save token in cookie
     * @param name key
     * @param value value
     * @param response response
     */
    private void saveTokenInCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // cookie.setSecure(true); // TODO enable this in production
        response.addCookie(cookie);
    }


}
