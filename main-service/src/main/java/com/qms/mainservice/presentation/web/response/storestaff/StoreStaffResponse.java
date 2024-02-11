package com.qms.mainservice.presentation.web.response.storestaff;

import lombok.Builder;

@Builder
public record StoreStaffResponse(
        Long staffId, // スタッフID
        Long storeId, // 店舗ID
        String lastName, // スタッフ姓
        String firstName, // スタッフ名
        String imageUrl, // 画像URL
        Boolean isActive, // 活動中フラグ
        Integer sortOrder, // 並び順
        String breakStartTime, // 休憩開始時間
        String breakEndTime // 休憩終了時間
) {
}
