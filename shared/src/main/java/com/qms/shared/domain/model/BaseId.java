package com.qms.shared.domain.model;


import java.io.Serializable;

public interface BaseId<T> extends Serializable {
    T value();
}
