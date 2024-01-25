package com.qms.shared.presentation;

public record Message(
        String message
) {
    public static Message of(String message) {
        if(message == null) return null;
        return new Message(message);
    }
}
