package com.qms.mainservice.domain.model.valueobject;

import java.math.BigDecimal;


public record SalesAmount(BigDecimal value) {
    public static SalesAmount of(BigDecimal value) {
        if(value == null) return null;
        if (value.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("value is negative");
        return new SalesAmount(value);
    }

    public SalesAmount add(SalesAmount other) {
        return new SalesAmount(this.value.add(other.value()));
    }
}