package com.qms.mainservice.presentation.web.response.storestaff;

import lombok.Builder;

@Builder
public record ActiveStaffResponse(
        Long storeId, // 店舗ID
        Long staffId, // スタッフID
        Integer sortOrder, // 並び順
        String lastName, // スタッフ姓
        String firstName, // スタッフ名
        String breakStartTime, // 休憩開始時間
        String breakEndTime, // 休憩終了時間
        Long reservationId // 予約ID
) {
}
