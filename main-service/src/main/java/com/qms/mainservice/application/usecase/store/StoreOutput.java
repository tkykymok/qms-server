package com.qms.mainservice.application.usecase.store;

import com.qms.mainservice.domain.model.valueobject.*;
import lombok.Builder;

import java.time.DayOfWeek;
import java.util.List;

@Builder
public record StoreOutput(
        StoreId storeId, // 店舗ID
        CompanyId companyId, // 企業ID
        StoreName storeName, // 店舗名
        PostalCode postalCode, // 郵便番号
        Address address, // 住所
        Latitude latitude, // 緯度
        Longitude longitude, // 経度
        PhoneNumber phoneNumber, // 電話番号
        HomePageUrl homePageUrl, // ホームページURL
        Flag isClosed, // 定休日フラグ
        String weekdayHours, // 平日の営業時間
        String holidayHours, // 土日祝の営業時間
        List<DayOfWeek> regularHolidays // 定休日
) {
}
