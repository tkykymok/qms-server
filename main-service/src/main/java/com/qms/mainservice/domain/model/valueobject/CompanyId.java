package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.BaseId;

public record CompanyId(Long value) implements BaseId<Long> {
    public static CompanyId of(Long value) {
        if(value == null) return null;
        return new CompanyId(value);
    }
}