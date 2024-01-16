package com.ty.shared.domain.model;


import java.io.Serializable;

public interface BaseId<T> extends Serializable {
    T value();
}
