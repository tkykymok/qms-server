package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record WaitingCount(Integer value) implements BaseId<Integer> {
    public static WaitingCount of(Integer value) {
        return new WaitingCount(value);
    }
}