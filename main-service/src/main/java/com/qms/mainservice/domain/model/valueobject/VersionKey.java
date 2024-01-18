package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record VersionKey(Integer value) implements ValueObject {
    public static VersionKey of(Integer value) {
        return new VersionKey(value);
    }

    public static VersionKey newVersion() {
        return new VersionKey(0);
    }
}