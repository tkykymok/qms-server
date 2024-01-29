package com.qms.mainservice.presentation.web.response.staff;

import lombok.Builder;

@Builder
public record StoreStaffResponse(
        Long staffId, // スタッフID
        Long storeId, // 店舗ID
        String lastName, // スタッフ姓
        String firstName, // スタッフ名
        Boolean isActive, // 活動中フラグ
        Long reservationId // 予約ID
) {
}
