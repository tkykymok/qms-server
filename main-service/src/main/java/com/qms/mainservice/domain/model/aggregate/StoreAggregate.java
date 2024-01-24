package com.qms.mainservice.domain.model.aggregate;

import com.qms.mainservice.domain.model.entity.StoreBusinessHour;
import com.qms.mainservice.domain.model.valueobject.*;
import com.qms.shared.domain.model.AggregateRoot;

import java.util.List;
import java.util.stream.Collectors;

public class StoreAggregate extends AggregateRoot<StoreId> {

    // 店舗
    private CompanyId companyId; // 企業ID
    private StoreName storeName; // 店舗名
    private PostalCode postalCode; // 郵便番号
    private Address address; // 住所
    private Latitude latitude; // 緯度
    private Longitude longitude; // 経度
    private PhoneNumber phoneNumber; // 電話番号
    private HomePageUrl homePageUrl; // ホームページURL

    // 店舗営業時間List
    private List<StoreBusinessHour> storeBusinessHours;

    private StoreAggregate() {}


    // DBから取得したデータをドメインオブジェクトに変換する
    public static StoreAggregate reconstruct(
            StoreId storeId,
            CompanyId companyId,
            StoreName storeName,
            PostalCode postalCode,
            Address address,
            Latitude latitude,
            Longitude longitude,
            PhoneNumber phoneNumber,
            HomePageUrl homePageUrl,
            List<StoreBusinessHour> storeBusinessHours
    ) {
        StoreAggregate storeAggregate = new StoreAggregate();
        storeAggregate.id = storeId;
        storeAggregate.companyId = companyId;
        storeAggregate.storeName = storeName;
        storeAggregate.postalCode = postalCode;
        storeAggregate.address = address;
        storeAggregate.latitude = latitude;
        storeAggregate.longitude = longitude;
        storeAggregate.phoneNumber = phoneNumber;
        storeAggregate.homePageUrl = homePageUrl;
        storeAggregate.storeBusinessHours = storeBusinessHours;
        return storeAggregate;
    }

    // 店舗営業時間Listから平日の営業時間を取得する
    public String getWeekdayHours(List<StoreBusinessHour> hours) {
        return hours.stream()
                .filter(hour -> hour.getKey().dayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue()
                        && hour.getKey().dayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue())
                .filter(hour -> !hour.getClosed().value())
                .map(hour -> hour.getOpenTime().value() + "〜" + hour.getCloseTime().value())
                .findFirst()
                .orElse("Closed");
    }

    // 店舗営業時間Listから土日祝の営業時間を取得する
    public String getHolidayHours(List<StoreBusinessHour> hours) {
        return hours.stream()
                .filter(hour -> hour.getKey().dayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()
                        || hour.getKey().dayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue())
                .filter(hour -> !hour.getClosed().value())
                .map(hour -> hour.getOpenTime().value() + "〜" + hour.getCloseTime().value())
                .findFirst()
                .orElse("Closed");
    }

    // 店舗営業時間Listから定休日一覧を取得する
    public List<DayOfWeek> getRegularHoliday(List<StoreBusinessHour> hours) {
        return hours.stream()
                .filter(hour -> hour.getClosed().value())
                .map(hour -> hour.getKey().dayOfWeek())
                .collect(Collectors.toList());
    }

}
