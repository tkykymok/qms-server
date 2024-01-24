package com.qms.mainservice.presentation.web.response.store;

import lombok.Builder;

import java.util.List;

@Builder
public record StoreResponse(
        Long storeId, // 店舗ID
        Long companyId, // 企業ID
        String storeName, // 店舗名
        String postalCode, // 郵便番号
        String address, // 住所
        Double latitude, // 緯度
        Double longitude, // 経度
        String phoneNumber, // 電話番号
        String homePageUrl, // ホームページURL
        Boolean isClosed, // 定休日フラグ
        String weekdayHours, // 平日の営業時間
        String holidayHours, // 土日祝の営業時間
        List<String> regularHolidays // 定休日
) {
}