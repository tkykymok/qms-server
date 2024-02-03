package com.qms.shared.domain.model;

import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public abstract class CompositeKeyBaseEntity<KEY> {
    protected KEY key;
    protected TrackingInfo trackingInfo;

    protected CompositeKeyBaseEntity() {}

    public void setKey(KEY key) {
        if (this.key == null) {
            this.key = key;
        } else {
            throw new IllegalStateException("IDは一度だけ設定できます");
        }
    }

}

