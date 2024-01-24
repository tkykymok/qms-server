package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record StaffId(Long value) implements BaseId<Long> {
    public static StaffId of(Long value) {
        if(value == null) return null;
        return new StaffId(value);
    }
}