package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record Position(Integer value) implements BaseId<Integer> {
    public static Position of(Integer value) {
        return new Position(value);
    }
}