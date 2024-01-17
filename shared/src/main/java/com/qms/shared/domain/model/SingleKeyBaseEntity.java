package com.qms.shared.domain.model;

import lombok.Getter;

@Getter
public abstract class SingleKeyBaseEntity<ID> {
    protected ID id;

    protected SingleKeyBaseEntity() {}

    public void setId(ID id) {
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IllegalStateException("IDは一度だけ設定できます");
        }
    }

}
