package com.qms.shared.domain.model;

import com.qms.shared.domain.model.valueobject.TrackingInfo;
import lombok.Getter;

@Getter
public abstract class SingleKeyBaseEntity<ID> {
    protected ID id;
    protected TrackingInfo trackingInfo;

    protected SingleKeyBaseEntity() {}

    public void setId(ID id) {
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IllegalStateException("IDは一度だけ設定できます");
        }
    }

}
