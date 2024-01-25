package com.qms.shared.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageHelper {

    private final MessageSource messageSource;

    public String getMessage(Locale locale, String code, String... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
