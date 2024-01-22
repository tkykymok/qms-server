package com.qms.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Formatter {
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return Optional.ofNullable(dateTime)
                .map(dt -> dt.format(DateTimeFormatter.ofPattern(pattern)))
                .orElse(null); // Or provide a default value or an empty string as required
    }
}
