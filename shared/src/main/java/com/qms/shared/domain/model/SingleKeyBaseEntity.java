package com.qms.shared.domain.model;

import com.qms.shared.domain.model.valueobject.UserType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class SingleKeyBaseEntity<ID> {
    protected ID id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected Long createdBy;
    protected UserType createdByType;
    protected Long updatedBy;
    protected UserType updatedByType;

    protected SingleKeyBaseEntity() {}

    public void setId(ID id) {
        if (this.id == null) {
            this.id = id;
        } else {
            throw new IllegalStateException("IDは一度だけ設定できます");
        }
    }

}
