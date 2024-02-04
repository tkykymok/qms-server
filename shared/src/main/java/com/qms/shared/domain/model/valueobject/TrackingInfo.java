package com.qms.shared.domain.model.valueobject;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TrackingInfo {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private UserType createdByType;
    private Long updatedBy;
    private UserType updatedByType;

    // プライベートコンストラクタ
    private TrackingInfo() {
    }

    public void setUpdateInfo() {
        setUpdatedAt();
        setUpdatedBy();
        setUpdatedByType();
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    private void setUpdatedBy() {
        // ログインユーザーIDを取得する TODO
        // this.updatedBy = LoginUser.get().getId();
        this.updatedBy = 1L;
    }

    private void setUpdatedByType() {
        // ログインユーザータイプを取得する TODO
        // this.updatedByType = LoginUser.get().getType();
        this.updatedByType = UserType.ADMIN;
    }

    public static TrackingInfo create() {
        // ログインユーザーIDを取得する TODO
        long createdBy = 1L;
        // ログインユーザータイプを取得する TODO
        UserType createdByType = UserType.ADMIN;

        TrackingInfo trackingInfo = new TrackingInfo();
        trackingInfo.createdAt = LocalDateTime.now();
        trackingInfo.updatedAt = LocalDateTime.now();
        trackingInfo.createdBy = createdBy;
        trackingInfo.createdByType = createdByType;
        trackingInfo.updatedBy = createdBy;
        trackingInfo.updatedByType = createdByType;
        return trackingInfo;
    }

    public static TrackingInfo reconstruct(
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long createdBy,
            UserType createdByType,
            Long updatedBy,
            UserType updatedByType
    ) {
        TrackingInfo trackingInfo = new TrackingInfo();
        trackingInfo.createdAt = createdAt;
        trackingInfo.updatedAt = updatedAt;
        trackingInfo.createdBy = createdBy;
        trackingInfo.createdByType = createdByType;
        trackingInfo.updatedBy = updatedBy;
        trackingInfo.updatedByType = updatedByType;
        return trackingInfo;
    }


}
