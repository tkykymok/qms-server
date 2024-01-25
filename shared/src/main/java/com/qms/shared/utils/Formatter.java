package com.qms.shared.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Formatter {
    public static String formatTime(LocalTime dateTime, String pattern) {
        return Optional.ofNullable(dateTime)
                .map(dt -> dt.format(DateTimeFormatter.ofPattern(pattern)))
                .orElse(null);
    }
}
