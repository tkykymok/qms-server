package com.qms.shared.domain.model;

import com.qms.shared.domain.model.valueobject.UserType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class CompositeKeyBaseEntity<KEY> {
    protected KEY key;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected Long createdBy;
    protected UserType createdByType;
    protected Long updatedBy;
    protected UserType updatedByType;

    protected CompositeKeyBaseEntity() {}

    public void setKey(KEY key) {
        if (this.key == null) {
            this.key = key;
        } else {
            throw new IllegalStateException("IDは一度だけ設定できます");
        }
    }

}

