package com.qms.mainservice.domain.model.valueobject;

import com.qms.shared.domain.model.ValueObject;

public record SortOrder(Integer value) implements ValueObject, Comparable<SortOrder> {
    public static SortOrder of(Integer value) {
        return new SortOrder(value);
    }

    @Override
    public int compareTo(SortOrder other) {
        return this.value.compareTo(other.value);
    }
}