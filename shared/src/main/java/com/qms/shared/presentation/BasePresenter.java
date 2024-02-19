package com.qms.shared.presentation;

import org.springframework.http.ResponseEntity;

public abstract class BasePresenter {
    public ResponseEntity<SuccessResponse> presentSuccess(String message) {
        return ResponseEntity.ok(new SuccessResponse(Message.of(message)));
    }
}

